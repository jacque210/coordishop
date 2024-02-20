package com.ohlala.styleshop.repository

import com.ohlala.styleshop.model.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ProductRepository : JpaRepository<Product, Long>, ProductQueryDslRepository {
  fun findTopByCategoryIdOrderByPriceAsc(categoryId: Long): Product?
  fun findAllByBrand(brand: String): List<Product>
  fun findAllByCategoryId(categoryId: Long): List<Product>

  fun deleteByBrand(brand: String)

  @Modifying
  @Query("update Product set brand = :brand where brand = :brand")
  fun updateAllByBrand(brand: String)

}

interface ProductQueryDslRepository {
//  fun findMinimumPriceProduct(categoryId: Long): Product?
}