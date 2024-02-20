package com.ohlala.styleshop.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "brand")
data class Brand(
  @Column(name = "name", nullable = false)
  var name: String,
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  var id: Long = 0
    protected set

  constructor() : this( "")

  fun isEmpty(): Boolean {
    return this.id == 0L || this.name.isEmpty()
  }

  fun update(name: String): Boolean {
    this.name = name
    return true
  }
}