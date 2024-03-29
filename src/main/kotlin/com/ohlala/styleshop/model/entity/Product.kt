package com.ohlala.styleshop.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table


@Entity
@Table(name = "product")
data class Product(

  @ManyToOne(fetch = FetchType.LAZY)
  val category: Category,

  @Column(name = "brand", nullable = false)
  val brand: String,

  @Column(name = "price", nullable = false)
  var price: Int,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  var id: Long = 0
    protected set

  fun update(price: Int): Boolean {
    this.price = price
    return true
  }
}
