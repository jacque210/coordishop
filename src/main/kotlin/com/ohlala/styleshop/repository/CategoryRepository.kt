package com.ohlala.styleshop.repository

import com.ohlala.styleshop.model.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>, CategoryQueryDslRepository

interface CategoryQueryDslRepository {

}