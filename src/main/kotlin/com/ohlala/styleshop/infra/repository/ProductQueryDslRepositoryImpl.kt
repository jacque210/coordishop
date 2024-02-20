package com.ohlala.styleshop.infra.repository

import com.ohlala.styleshop.common.jpa.Querydsl5RepositorySupport
import com.ohlala.styleshop.model.entity.Product
import com.ohlala.styleshop.model.entity.QCategory
import com.ohlala.styleshop.model.entity.QProduct
import com.ohlala.styleshop.repository.ProductQueryDslRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ProductQueryDslRepositoryImpl : ProductQueryDslRepository, Querydsl5RepositorySupport(Product::class.java) {
//  override fun findMinimumPriceProduct(categoryId: Long): Product? {
//    return select(
//      QProduct.product
//    ).from(QProduct.product)
//      .where(QProduct.product.category.id.eq(categoryId))
//      .orderBy(QProduct.product.price.asc())
//      .limit(1)
//      .fetchOne()
//  }
}