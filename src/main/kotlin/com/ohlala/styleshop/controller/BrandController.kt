package com.ohlala.styleshop.controller

import com.ohlala.styleshop.service.BrandService
import com.ohlala.styleshop.service.request.BrandAddRequest
import com.ohlala.styleshop.service.request.BrandRemoveRequest
import com.ohlala.styleshop.service.request.BrandUpdateRequest
import com.ohlala.styleshop.service.request.ProductRemoveRequest
import com.ohlala.styleshop.service.response.BrandCudResponse
import com.ohlala.styleshop.service.response.ProductCudResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/brand"])
class BrandController(
  private val brandService: BrandService,
) {
  @PostMapping("/add")
  fun addProduct(
    @RequestBody brandAddRequest: BrandAddRequest
  ): BrandCudResponse {
    return brandService.addBrand(brandAddRequest)
  }

  @PostMapping("/update")
  fun updateBrand(
    @RequestBody brandUpdateRequest: BrandUpdateRequest
  ): BrandCudResponse {
    return brandService.updateBrand(brandUpdateRequest)
  }

  @PostMapping("/remove")
  fun removeBrand(
    @RequestBody brandRemoveRequest: BrandRemoveRequest
  ): BrandCudResponse {
    return brandService.removeBrand(brandRemoveRequest)
  }
}