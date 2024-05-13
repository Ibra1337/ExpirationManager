package com.example.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.databinding.UneditableProductBinding
import com.example.utils.CATEGORY
import com.example.domain.Product

class DisplayProdDialog(context: Context ,
                        var product: Product
        ) :Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var dialogBinding = UneditableProductBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        val categoriesArr = CATEGORY.values().map { it.name }



        dialogBinding.apply {



            productNameInput.text = product.productName
            selectedDateOutput.text = product.expirationDate.toString()
            categoryTv.text = product.category.toString();
            if (product.quantity != null)
            {

                productQuantityTv.text = product.quantity.toString()
            }else {
                productQuantityLabel.visibility = View.GONE
                productQuantityTv.visibility = View.GONE
            }



        }

        dialogBinding.okButton.setOnClickListener{
            this.dismiss()
        }

    }


}