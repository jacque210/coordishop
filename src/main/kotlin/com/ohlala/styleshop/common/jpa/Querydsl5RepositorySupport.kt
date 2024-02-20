package com.ohlala.styleshop.common.jpa

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.querydsl.EntityPathResolver
import org.springframework.data.querydsl.SimpleEntityPathResolver
import org.springframework.stereotype.Repository
import org.springframework.util.Assert

@Repository
abstract class Querydsl5RepositorySupport(private val domainClass: Class<*>) {
  private lateinit var querydsl: Querydsl
  private lateinit var entityManager: EntityManager
  private lateinit var queryFactory: JPAQueryFactory

  @Autowired
  fun setEntityManager(entityManager: EntityManager) {
    Assert.notNull(entityManager, "EntityManager must not be null!")
    val entityInformation = JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager)
    val resolver: EntityPathResolver = SimpleEntityPathResolver.INSTANCE
    val path = resolver.createPath(entityInformation.javaType)
    this.entityManager = entityManager
    this.querydsl = Querydsl(entityManager, PathBuilder<Any>(path.type, path.metadata))
    this.queryFactory = JPAQueryFactory(entityManager)
  }

  @PostConstruct
  fun validate() {
    Assert.notNull(entityManager, "EntityManager must not be null!")
    Assert.notNull(querydsl, "Querydsl must not be null!")
    Assert.notNull(queryFactory, "QueryFactory must not be null!")
  }

  protected fun getQueryFactory() = queryFactory
  protected fun getQuerydsl() = querydsl
  protected fun getEntityManager() = entityManager

  protected fun <T> select(expr: Expression<T>): JPAQuery<T> = getQueryFactory().select(expr)
  protected fun selectOne(): JPAQuery<Int> = getQueryFactory().selectOne()
  protected fun <T> selectFrom(from: EntityPath<T>): JPAQuery<T> = getQueryFactory().selectFrom(from)

}