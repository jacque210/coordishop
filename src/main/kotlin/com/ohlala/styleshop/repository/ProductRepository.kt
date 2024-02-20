package com.ohlala.styleshop.repository

import com.ohlala.styleshop.model.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>, ProductQueryDslRepository {
  fun findTopByCategoryIdOrderByPriceAsc(categoryId: Long): Product?
  fun findAllByBrand(brand: String): List<Product>
}

interface ProductQueryDslRepository {
//  fun findMinimumPriceProduct(categoryId: Long): Product?
}