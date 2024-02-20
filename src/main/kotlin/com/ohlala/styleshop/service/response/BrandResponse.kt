package com.ohlala.styleshop.service.response

import org.springframework.http.HttpStatus

data class BrandResponse(
  val id: Long,
  val brand: String,
)

data class BrandCudResponse(
  val statusCode: HttpStatus,
  val errorMessage: String?,
)
