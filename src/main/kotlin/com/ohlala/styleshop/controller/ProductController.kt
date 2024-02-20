package com.ohlala.styleshop.controller

import com.ohlala.styleshop.service.ProductService
import com.ohlala.styleshop.service.request.ProductAddRequest
import com.ohlala.styleshop.service.request.ProductRemoveRequest
import com.ohlala.styleshop.service.request.ProductUpdateRequest
import com.ohlala.styleshop.service.response.ProductCudResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/product"])
class ProductController(
  private val productService: ProductService
) {
  @PostMapping("/add")
  fun addProduct(
    @RequestBody productAddRequest: ProductAddRequest
  ): ProductCudResponse {
    return productService.addProduct(productAddRequest)
  }

  @PostMapping("/updatePrice")
  fun updateProduct(
    @RequestBody productUpdateRequest: ProductUpdateRequest
  ): ProductCudResponse {
    return productService.updateProductPrice(productUpdateRequest)
  }

  @PostMapping("/remove")
  fun removeProduct(
    @RequestBody productRemoveRequest: ProductRemoveRequest
  ): ProductCudResponse {
    return productService.removeProduct(productRemoveRequest.id)
  }
}