package com.ohlala.styleshop.controller

import com.ohlala.styleshop.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/category"])
class CategoryController(
  private val categoryService: CategoryService
) {

  @GetMapping("/{id}")
  fun getCategoryNameById(@PathVariable id: Long): String {
    return categoryService.findNameById(id = id)
      ?: throw IllegalArgumentException("요청한 카테고리 ID에 해당하는 값이 없습니다.")
  }
}