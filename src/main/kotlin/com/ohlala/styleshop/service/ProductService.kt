package com.ohlala.styleshop.service

import com.ohlala.styleshop.model.entity.Brand
import com.ohlala.styleshop.model.entity.Category
import com.ohlala.styleshop.model.entity.Product
import com.ohlala.styleshop.repository.BrandRepository
import com.ohlala.styleshop.repository.CategoryRepository
import com.ohlala.styleshop.repository.ProductRepository
import com.ohlala.styleshop.service.request.ProductAddRequest
import com.ohlala.styleshop.service.request.ProductUpdateRequest
import com.ohlala.styleshop.service.response.BrandProduct
import com.ohlala.styleshop.service.response.BrandProductResponse
import com.ohlala.styleshop.service.response.MinMaxPriceProduct
import com.ohlala.styleshop.service.response.MinMaxPriceProductInfoResponse
import com.ohlala.styleshop.service.response.MinPriceBrandInfoResponse
import com.ohlala.styleshop.service.response.MinPriceByCategoryProductResponse
import com.ohlala.styleshop.service.response.MinPriceInfo
import com.ohlala.styleshop.service.response.ProductCudResponse
import com.ohlala.styleshop.service.response.ProductResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ProductService(
  private val productRepository: ProductRepository,
  private val categoryRepository: CategoryRepository,
  private val brandRepository: BrandRepository,
) {

  fun getMinimumPriceProducts(): MinPriceByCategoryProductResponse {
    // 전체 카테고리 목록 조회
    val categories = categoryRepository.findAll()
    if (categories.isEmpty()) {
      return MinPriceByCategoryProductResponse(0, emptyList())
    }

    // 각 카테고리 별 최저가 조회
    val products = mutableListOf<ProductResponse>()
    categories.map { category ->
      productRepository.findTopByCategoryIdOrderByPriceAsc(categoryId = category.id)
        ?.let {
          products.add(
            ProductResponse(
              categoryId = it.category.id,
              categoryName = it.category.name,
              brand = it.brand,
              price = it.price
            )
          )
        }
    }
    // 카테고리 최저가 정보를 응답 객체에 저장
    return MinPriceByCategoryProductResponse(
      totalAmount = products.sumOf { it.price },
      productList = products
    )
  }

  fun getMinimumPriceBrandInfo(): MinPriceBrandInfoResponse {
    val brandProductMap = mutableMapOf<String, List<BrandProduct>>()
    val brandPriceMap = mutableMapOf<String, Int>()

    val brandList = brandRepository.findAll()
    // 브랜드별 가격 정보 조회
    brandList.map { brand ->
      val brandPriceList = mutableListOf<BrandProduct>()
      productRepository.findAllByBrand(brand.name).map { product ->
        brandPriceList.add(
          BrandProduct(
            brand = product.brand,
            categoryName = product.category.name,
            price = product.price,
          )
        )

        brandProductMap.put(brand.name, brandPriceList)
        brandPriceMap.put(brand.name, brandPriceList.sumOf { it.price })
      }
    }

    // 최저가 브랜드 필터링
    val minPriceBrandTotalAmountPair = brandPriceMap.toList().minByOrNull { it.second }!!
    val categoryList = mutableListOf<BrandProductResponse>()
    brandProductMap[minPriceBrandTotalAmountPair.first]?.map { brandProduct ->
      categoryList.add(
        BrandProductResponse(
          categoryName = brandProduct.categoryName,
          price = brandProduct.price,
        )
      )
    } ?: run {
      throw RuntimeException("${minPriceBrandTotalAmountPair.first} 브랜드와 매핑되는 상품 정보가 없습니다.")
    }

    // 응답 객체에 저장
    return MinPriceBrandInfoResponse(
      minPriceInfo = MinPriceInfo(
        brand = minPriceBrandTotalAmountPair.first,
        categoryList = categoryList,
        totalAmount = minPriceBrandTotalAmountPair.second,
      )
    )
  }

  fun getMinMaxPriceProductInfo(categoryName: String): MinMaxPriceProductInfoResponse {
    // 카테고리명으로 카테고리 아이디 조회
    val category = categoryRepository.findByName(categoryName)
      ?: run {
        throw IllegalArgumentException("존재하지 않는 카테고리입니다.")
      }

    if (category.isEmpty()) {
      throw IllegalArgumentException("존재하지 않는 카테고리입니다.")
    }

    // 카테고리 아이디 해당하는 최저 및 최고 상품가격 조회
    val productList = productRepository.findAllByCategoryId(category.id)
    val minPriceProduct = productList.minByOrNull { it.price } ?: Product(Category(), "", 0)
    val maxPriceProduct = productList.maxByOrNull { it.price } ?: Product(Category(), "", 0)

    return MinMaxPriceProductInfoResponse(
      categoryName = category.name,
      minPriceProduct = MinMaxPriceProduct(
        brand = minPriceProduct.brand,
        price = minPriceProduct.price,
      ),
      maxPriceProduct = MinMaxPriceProduct(
        brand = maxPriceProduct.brand,
        price = maxPriceProduct.price,
      ),
    )
  }

  @Transactional
  fun addProduct(productAddRequest: ProductAddRequest): ProductCudResponse {
    if (isInvalidInput(productAddRequest)) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "입력값이 올바르지 않습니다. 이름은 512자 이내로 입력하세요.",
      )
    }
    val category = categoryRepository.findByName(productAddRequest.categoryName) ?: Category()
    if (category.isEmpty()) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "올바른 카테고리 이름을 입력하세요.",
      )
    }

    val brand = brandRepository.findByName(productAddRequest.brand) ?: Brand()
    if (brand.isEmpty()) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "올바른 브랜드 이름을 입력하세요.",
      )
    }

    productRepository.save(Product(
      category = category,
      brand = productAddRequest.brand,
      price = productAddRequest.price,
    ))
    return ProductCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = "",
    )
  }

  @Transactional
  fun updateProductPrice(productUpdateRequest: ProductUpdateRequest): ProductCudResponse {
    if (isInvalidInput(productUpdateRequest)) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "가격이 입력 범위를 벗어납니다.",
      )
    }
    val product = productRepository.findById(productUpdateRequest.id)
    if (product.isEmpty) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "요청한 상품 정보가 없습니다.",
      )
    }

    product.get().update(productUpdateRequest.price)
    return ProductCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = "",
    )
  }

  @Transactional
  fun removeProduct(id: Long): ProductCudResponse {
    val product = productRepository.findById(id)
    if (product.isEmpty) {
      return ProductCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "요청한 상품 정보가 없습니다.",
      )
    }

    productRepository.delete(product.get())
    return ProductCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = "",
    )
  }

  fun isInvalidInput(productAddRequest: ProductAddRequest): Boolean {
    if (productAddRequest.brand.length > 512 || productAddRequest.categoryName.length > 512) {
      return true
    }

    if (productAddRequest.price > Integer.MAX_VALUE || productAddRequest.price <= 0) {
      return true
    }
    return false
  }

  fun isInvalidInput(productUpdateRequest: ProductUpdateRequest): Boolean {
    return productUpdateRequest.price > Integer.MAX_VALUE || productUpdateRequest.price <= 0
  }
}