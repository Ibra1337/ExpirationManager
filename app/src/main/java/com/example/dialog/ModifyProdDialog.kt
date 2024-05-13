package com.example.dialog

import com.example.adapter.ProductAdapter
import android.content.Context
import android.os.Bundle
import com.example.utils.CATEGORY
import com.example.domain.Product

class ModifyProdDialog(
    context: Context,
    private val products: ProductAdapter,
    private val position: Int ) : AbstractProductEditorDialog(context){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val selectedProduct = products.getProduct(position)
        val categoryArray = CATEGORY.values().map { it.name }

        super.dialogBinding.apply {
            productNameInput.setText(selectedProduct.productName)
            selectedProduct.expirationDate.apply {
                selectedDateOutput.text = buildString {
                    append(dayOfMonth.toString())
                    append("-")
                    append(month.value)
                    append("-")
                    append(year)
                }
            }
            val categoryPosition = categoryArray.indexOf(selectedProduct.category.name)
            if (categoryPosition != -1) {
                categorySpinner.setSelection(categoryPosition)
            }

            if (selectedProduct.quantity != null) {
                productQuatitytyIput.setText(selectedProduct.quantity.toString())
            }
        }
    }


    override fun handleSubmit(product: Product) {
        products.modifyProduct(product,position)
    }

}