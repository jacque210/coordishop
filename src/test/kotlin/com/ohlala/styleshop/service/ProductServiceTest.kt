package com.ohlala.styleshop.service

import com.ohlala.styleshop.model.entity.Brand
import com.ohlala.styleshop.model.entity.Category
import com.ohlala.styleshop.model.entity.Product
import com.ohlala.styleshop.repository.BrandRepository
import com.ohlala.styleshop.repository.CategoryRepository
import com.ohlala.styleshop.repository.ProductRepository
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk

class ProductServiceTest : ExpectSpec(
  {
    val productRepository = mockk<ProductRepository>(relaxed = true)
    val categoryRepository = mockk<CategoryRepository>(relaxed = true)
    val brandRepository = mockk<BrandRepository>(relaxed = true)

    val productService = spyk<ProductService>(
      ProductService(
        productRepository = productRepository,
        categoryRepository = categoryRepository,
        brandRepository = brandRepository,
      )
    )

    afterEach {
      clearAllMocks()
    }

    context("getMinimumPriceProducts TEST") {
      expect("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회 - 총액과 리스트 정보를 확인한다.") {
        every {
          categoryRepository.findAll()
        }.returns(
          listOf(
            Category(
              id = 1,
              name = "상의"
            ),
            Category(
              id = 2,
              name = "아우터"
            ),
            Category(
              id = 3,
              name = "바지"
            ),
          )
        )

        every { productRepository.findTopByCategoryIdOrderByPriceAsc(1) } returns Product(
          category = Category(1, "상의"),
          price = 1000,
          brand = "A"
        )
        every { productRepository.findTopByCategoryIdOrderByPriceAsc(2) } returns Product(
          category = Category(2, "아우터"),
          price = 2000,
          brand = "B"
        )
        every { productRepository.findTopByCategoryIdOrderByPriceAsc(3) } returns Product(
          category = Category(3, "바지"),
          price = 1500,
          brand = "C"
        )

        every {
          brandRepository.findAll()
        }.returns(
          listOf(
            Brand(
              name = "A"
            ),
            Brand(
              name = "B"
            ),
            Brand(
              name = "C"
            ),
          )
        )

        // when
        val result = productService.getMinimumPriceProducts()

        // then
        result.productList.size.shouldBe(3)
        result.totalAmount.shouldBe(4500)
      }

      expect("카테고리에 데이터가 없으면 빈리스트를 얻는다.") {
        // given
        every {
          categoryRepository.findAll()
        } returns emptyList()

        // when
        val result = productService.getMinimumPriceProducts()

        // then
        result.productList.size.shouldBe(0)
        result.totalAmount.shouldBe(0)
      }
    }

    context("getMinimumPriceBrandInfo TEST") {
      every { productRepository.findAllByBrand(any()) } returns listOf(
        Product(Category(1, "상의"), "A", 10000),
        Product(Category(2, "아우터"), "A", 20000)
      )

      every { productRepository.findAllByBrand("A") } returns listOf(
        Product(Category(1, "상의"), "A", 1000),
        Product(Category(2, "아우터"), "A", 2000)
      )
      every { productRepository.findAllByBrand("B") } returns listOf(
        Product(Category(1, "상의"), "B", 2000),
        Product(Category(2, "아우터"), "B", 3000)
      )
      every { productRepository.findAllByBrand("C") } returns listOf(
        Product(Category(1, "상의"), "C", 3000),
        Product(Category(2, "아우터"), "C", 4000)
      )

      expect("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회") {
        // when
        val result = productService.getMinimumPriceBrandInfo()

        // then
        result.minPriceInfo.brand.shouldBe("A")
        result.minPriceInfo.totalAmount.shouldBe(3000)
      }

      expect("브랜드에 매핑되는 상품이 조회되지 않으면 에러가 발생한다.") {
        // given
        every { productRepository.findAllByBrand("A") } returns emptyList()

        // then
        shouldThrowAny { productService.getMinimumPriceBrandInfo() }
      }
    }

    context("getMinMaxPriceProductInfo TEST") {
      //given
      every { categoryRepository.findByName("상의") } returns Category(1, "상의")
      every { productRepository.findAllByCategoryId(1) } returns listOf(
        Product(Category(1, "상의"), "A", 1000),
        Product(Category(1, "상의"), "B", 2000),
        Product(Category(1, "상의"), "C", 3000)
      )

      expect("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회") {
        // when
        val result = productService.getMinMaxPriceProductInfo("상의")

        // then
        result.minPriceProduct.price.shouldBe(1000)
        result.minPriceProduct.brand.shouldBe("A")

        result.maxPriceProduct.price.shouldBe(3000)
        result.maxPriceProduct.brand.shouldBe("C")
      }

      expect("카테고리가 조회되지 않으면 에러가 발생한다.") {
        every { categoryRepository.findByName("치킨") } returns null

        // when
        shouldThrowAny { productService.getMinMaxPriceProductInfo("치킨") }
      }
    }
  }
)
