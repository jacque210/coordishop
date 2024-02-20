package com.ohlala.styleshop.service

import com.ohlala.styleshop.model.entity.Category
import com.ohlala.styleshop.model.entity.Product
import com.ohlala.styleshop.repository.CategoryRepository
import com.ohlala.styleshop.repository.ProductRepository
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

    val productService = spyk<ProductService>(
      ProductService(
        productRepository = productRepository,
        categoryRepository = categoryRepository,
      )
    )

    afterEach {
      clearAllMocks()
    }

    context("findMinimumPriceProducts TEST") {

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
          id = 1,
          category = Category(1, "상의"),
          price = 1000,
          brand = "A"
        )
        every { productRepository.findTopByCategoryIdOrderByPriceAsc(2) } returns Product(
          id = 2,
          category = Category(2, "아우터"),
          price = 2000,
          brand = "B"
        )
        every { productRepository.findTopByCategoryIdOrderByPriceAsc(3) } returns Product(
          id = 3,
          category = Category(3, "바지"),
          price = 1500,
          brand = "C"
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
  }
)
