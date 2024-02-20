package com.ohlala.styleshop.model.entity

import com.ohlala.styleshop.service.response.CategoryResponse
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "category")
data class Category(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Long,

  @Column(name = "name", nullable = false)
  val name: String,
) {

  fun toResponse(): CategoryResponse? {
    return CategoryResponse(
      id = id,
      name = name,
    )
  }
}
