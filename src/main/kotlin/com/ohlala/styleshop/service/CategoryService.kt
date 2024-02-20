package com.ohlala.styleshop.service

import com.ohlala.styleshop.repository.CategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService(
  private val categoryRepository: CategoryRepository
) {
  fun findNameById(id: Long): String? {
    return categoryRepository.findByIdOrNull(id)
      ?.toResponse()?.name
  }
}