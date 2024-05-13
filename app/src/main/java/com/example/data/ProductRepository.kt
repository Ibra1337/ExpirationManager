package com.example.data

import com.example.domain.Product
import com.example.utils.Filter

interface ProductRepository {


    fun updateProduct(product: Product)

    fun addProduct(product: Product)

    fun removeProduct(product: Product)

    fun filter(filter: Filter): List<Product>


    fun asMutableList(): MutableList<Product>
}