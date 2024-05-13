package com.example.domain

import com.example.utils.CATEGORY
import java.time.LocalDate

data class Product(
    var productName : String,
    var expirationDate :LocalDate,
    var category : CATEGORY,
    var quantity : Int? = null,
    ) {

    val expired: Boolean
        get() = !expirationDate.isAfter(LocalDate.now())

}