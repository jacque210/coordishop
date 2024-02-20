package com.ohlala.styleshop.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne


@Entity(name = "product")
data class Product(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  val id: Long,

  @ManyToOne(fetch = FetchType.LAZY)
  val category: Category,

  @Column(name = "brand", nullable = false)
  val brand: String,

  @Column(name = "price", nullable = false)
  val price: Int,
)
