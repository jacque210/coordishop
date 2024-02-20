package com.ohlala.styleshop.app

import com.ohlala.styleshop.service.CategoryService
import org.springframework.stereotype.Service

@Service
class Coordinator(
  private val categoryService: CategoryService
) {
  fun getMinimumPriceByCategory(categoryId: Long): Int {
    return 0
  }
}