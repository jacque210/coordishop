package com.ohlala.styleshop.controller

import com.ohlala.styleshop.service.ProductService
import com.ohlala.styleshop.service.response.MinMaxPriceProductInfoResponse
import com.ohlala.styleshop.service.response.MinPriceBrandInfoResponse
import com.ohlala.styleshop.service.response.MinPriceByCategoryProductResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/price"])
class PriceController(
  private val productService: ProductService
) {

  @GetMapping("/total")
  fun getTotal(): String {
    return "total"
  }

  @GetMapping("/minPriceProducts")
  fun getMinPriceProducts(): MinPriceByCategoryProductResponse {
    return productService.getMinimumPriceProducts()
  }

  @GetMapping("/minPriceBrandInfo")
  fun getMinPriceBrandInfo(): MinPriceBrandInfoResponse {
    return productService.getMinimumPriceBrandInfo()
  }

  @GetMapping("/minMaxPriceProductInfo/{categoryName}")
  fun getMinMaxPriceProductInfo(@PathVariable categoryName: String): MinMaxPriceProductInfoResponse {
    return productService.getMinMaxPriceProductInfo(categoryName)
  }
}