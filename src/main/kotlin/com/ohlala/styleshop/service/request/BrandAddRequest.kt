package com.ohlala.styleshop.service.request

data class BrandAddRequest(
  val name: String,
)

data class BrandUpdateRequest(
  val id: Long,
  val name: String,
)

data class BrandRemoveRequest(
  val id: Long,
)
