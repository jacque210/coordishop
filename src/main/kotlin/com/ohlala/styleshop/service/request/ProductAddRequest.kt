package com.ohlala.styleshop.service.request

data class ProductAddRequest(
  val categoryName: String,
  val brand: String,
  val price: Int,
)

data class ProductUpdateRequest(
  val id: Long,
  val price: Int,
)

data class ProductRemoveRequest(
  val id: Long,
)
