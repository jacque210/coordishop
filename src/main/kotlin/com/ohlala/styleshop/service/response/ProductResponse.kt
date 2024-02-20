package com.ohlala.styleshop.service.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.ohlala.styleshop.common.util.NumberFormatDeserializer
import org.springframework.http.HttpStatus

data class ProductResponse(
  val categoryId: Long,
  val categoryName: String,
  val brand: String,
  val price: Int,
)

data class MinPriceByCategoryProductResponse(
  val totalAmount: Int,
  val productList: List<ProductResponse>,
)

data class MinPriceBrandInfoResponse(
  @JsonProperty("최저가")
  val minPriceInfo: MinPriceInfo,
)

data class MinPriceInfo(
  @JsonProperty("브랜드")
  val brand: String,

  @JsonProperty("카테고리")
  val categoryList: List<BrandProductResponse>,

  @JsonDeserialize(using = NumberFormatDeserializer::class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "%,d")
  @JsonProperty("총액")
  val totalAmount: Int,
)

data class BrandProductResponse(
  @JsonProperty("카테고리")
  val categoryName: String,

  @JsonDeserialize(using = NumberFormatDeserializer::class)
  @JsonProperty("가격")
  val price: Int,
)

data class BrandProduct(
  val brand: String,
  val categoryName: String,
  val price: Int,
)

data class MinMaxPriceProductInfoResponse(
  @JsonProperty("카테고리")
  val categoryName: String,
  @JsonProperty("최저가")
  val minPriceProduct: MinMaxPriceProduct,
  @JsonProperty("최고가")
  val maxPriceProduct: MinMaxPriceProduct,
)

data class MinMaxPriceProduct(
  @JsonProperty("브랜드")
  val brand: String,

  @JsonDeserialize(using = NumberFormatDeserializer::class)
  @JsonProperty("가격")
  val price: Int,
)

data class ProductCudResponse(
  val statusCode: HttpStatus,
  val errorMessage: String?,
)