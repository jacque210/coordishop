package com.ohlala.styleshop.infra.category.repository

import com.ohlala.styleshop.model.entity.Category
import com.ohlala.styleshop.repository.CategoryQueryDslRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CategoryQueryDslRepositoryImpl : QuerydslRepositorySupport(Category::class.java), CategoryQueryDslRepository {

}