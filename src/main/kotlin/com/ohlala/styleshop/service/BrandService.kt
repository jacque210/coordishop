package com.ohlala.styleshop.service

import com.ohlala.styleshop.model.entity.Brand
import com.ohlala.styleshop.repository.BrandRepository
import com.ohlala.styleshop.repository.ProductRepository
import com.ohlala.styleshop.service.request.BrandAddRequest
import com.ohlala.styleshop.service.request.BrandRemoveRequest
import com.ohlala.styleshop.service.request.BrandUpdateRequest
import com.ohlala.styleshop.service.response.BrandCudResponse
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BrandService(
  private val brandRepository: BrandRepository,
  private val productRepository: ProductRepository,
) {
  @Transactional
  fun addBrand(brandAddRequest: BrandAddRequest): BrandCudResponse {
    if (isInvalidInput(brandAddRequest.name)) {
      return BrandCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "입력값이 올바르지 않습니다."
      )
    }

    brandRepository.findByName(brandAddRequest.name)?.let {
      return BrandCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "이미 동일한 브랜드가 있습니다."
      )
    }

    brandRepository.save(Brand(
      name = brandAddRequest.name,
    ))

    return BrandCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = ""
    )
  }

  @Transactional
  fun updateBrand(brandUpdateRequest: BrandUpdateRequest): BrandCudResponse {
    if (isInvalidInput(brandUpdateRequest.name)) {
      return BrandCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "입력값이 올바르지 않습니다."
      )
    }

    val brand = brandRepository.findById(brandUpdateRequest.id)
    if (brand.isEmpty) {
      return BrandCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "수정할 브랜드가 없습니다."
      )
    }

    brand.get().update(brandUpdateRequest.name)
    // 상품 정보도 같이 갱신
    productRepository.updateAllByBrand(brandUpdateRequest.name)

    return BrandCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = ""
    )
  }

  @Transactional
  fun removeBrand(brandRemoveRequest: BrandRemoveRequest): BrandCudResponse {
    val brand = brandRepository.findById(brandRemoveRequest.id)
    if (brand.isEmpty) {
      return BrandCudResponse(
        statusCode = HttpStatus.NOT_ACCEPTABLE,
        errorMessage = "삭제할 브랜드가 없습니다."
      )
    }

    brandRepository.delete(brand.get())
    // 등록된 상품도 같이 삭제
    productRepository.deleteByBrand(brand.get().name)
    return BrandCudResponse(
      statusCode = HttpStatus.OK,
      errorMessage = ""
    )
  }

  private fun isInvalidInput(name: String): Boolean {
    return name.isEmpty() || name.length > 512
  }
}