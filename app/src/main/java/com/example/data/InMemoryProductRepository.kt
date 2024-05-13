package com.example.data

import com.example.domain.Product
import com.example.utils.Filter
import java.util.SortedSet
import java.util.TreeSet

class InMemoryProductRepository: ProductRepository {

    private var products: SortedSet<Product> = TreeSet(compareBy { it.expirationDate })



    override fun updateProduct(product: Product) {

    }

    override fun addProduct(product: Product) {
        products.add(product)
    }

    override fun removeProduct(product: Product) {
        products.remove(product)
    }

    override fun filter(filter: Filter): List<Product> {
        return products.filter { p -> filter.prodMatchesCriteria(p) }
    }

    override fun asMutableList(): MutableList<Product> {
        return products.toMutableList();
    }
}