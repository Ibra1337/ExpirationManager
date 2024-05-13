package com.example.utils

import com.example.domain.Product

class Filter(
    val category: CATEGORY?,
    val expired: Boolean?
) {


    fun prodMatchesCriteria(product: Product) : Boolean
    {

        var categoryMatch = category == null || product.category == this.category
        var validMach = expired == null || product.expired == expired

        return categoryMatch && validMach
    }
}