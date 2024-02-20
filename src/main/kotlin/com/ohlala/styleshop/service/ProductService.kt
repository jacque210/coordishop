package com.ohlala.styleshop.service

import com.ohlala.styleshop.repository.CategoryRepository
import com.ohlala.styleshop.repository.ProductRepository
import com.ohlala.styleshop.service.response.BrandProduct
import com.ohlala.styleshop.service.response.BrandProductResponse
import com.ohlala.styleshop.service.response.MinPriceBrandInfoResponse
import com.ohlala.styleshop.service.response.MinPriceByCategoryProductResponse
import com.ohlala.styleshop.service.response.MinPriceInfo
import com.ohlala.styleshop.service.response.ProductResponse
import org.springframework.stereotype.Service

@Service
class ProductService(
  private val productRepository: ProductRepository,
  private val categoryRepository: CategoryRepository,
) {
  companion object {
    private val brandList = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I")
  }

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
    // 브랜드별 가격 정보 조회
    brandList.map { brand ->
      val brandPriceList = mutableListOf<BrandProduct>()
      productRepository.findAllByBrand(brand).map { product ->
        brandPriceList.add(
          BrandProduct(
            brand = product.brand,
            categoryName = product.category.name,
            price = product.price,
          )
        )

        brandProductMap.put(brand, brandPriceList)
        brandPriceMap.put(brand, brandPriceList.sumOf { it.price })
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


}