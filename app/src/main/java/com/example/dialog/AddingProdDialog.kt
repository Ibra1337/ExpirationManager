package com.example.dialog

import com.example.adapter.ProductAdapter
import android.content.Context
import com.example.domain.Product


class AddingProdDialog(context: Context, val products: ProductAdapter) :
    AbstractProductEditorDialog(context)  {

    

    override fun handleSubmit(product: Product) {
        products.addProduct(product);
    }








}
