package com.ohlala.styleshop.repository

import com.ohlala.styleshop.model.entity.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, Long> {
  fun findByName(name: String): Brand?
}