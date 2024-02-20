package com.ohlala.styleshop.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/price"])
class PriceController {

  @GetMapping("/total")
  fun getTotal(): String {
    return "total"
  }
}