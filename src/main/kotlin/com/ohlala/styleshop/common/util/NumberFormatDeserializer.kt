package com.ohlala.styleshop.common.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.text.NumberFormat

class NumberFormatDeserializer : JsonDeserializer<String>() {
  override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): String {
    val input = p?.intValue ?: 0
    return "%,d".format(input)
  }
}