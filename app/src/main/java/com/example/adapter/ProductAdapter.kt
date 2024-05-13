package com.example.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemProductBinding
import com.example.databinding.RemovalDialogBinding
import com.example.dialog.DisplayProdDialog
import com.example.domain.IMainActivity
import com.example.dialog.ModifyProdDialog
import com.example.domain.Product
import com.example.utils.Filter
import java.time.LocalDate

class ProductAdapter(
    products: List<Product>,
    private val mainActivity: IMainActivity,
    internal var filter: Filter = Filter(null, null)
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)


    private val products: MutableList<Product> = mutableListOf<Product>().apply {
        addAll(products)
    }

    private var productAdapter : ProductAdapter = this


    private var _itemQuantityCounter =0

    val itemQuantityCounter
        get() =_itemQuantityCounter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val curProduct = products[position]
        holder.binding.apply {

            productNameTv.text = curProduct.productName
            expirationDateTv.text = curProduct.expirationDate.toString()
            categoryTv.text = curProduct.category.toString()
            if (curProduct.quantity != null)
                quantityTv.text = curProduct.quantity.toString()
            else
                quantityTv.text = ""

            colorEntires(curProduct , root , position)

        }
        _itemQuantityCounter = getDisplayedProductsTotalQuantity()
        updateCounter(0)
    }

    private fun colorEntires(curProduct: Product , root: ConstraintLayout , position: Int) {
        if (curProduct.expirationDate.isBefore(LocalDate.now()) )
        {
            root.apply {
                setBackgroundColor(Color.RED)
                setOnClickListener {
                    val d = DisplayProdDialog(context, curProduct)
                    d.show()
                }
            }
        }
        else if (curProduct.expirationDate.isBefore(LocalDate.now().plusDays(7))) {

            createViewHolder(root,curProduct,position,Color.YELLOW)
        }else {

            createViewHolder(root,curProduct,position,Color.GREEN)
        }
    }

    private fun createViewHolder(root :ConstraintLayout , curProduct: Product , position: Int , color: Int)
    {
        root.apply {
            setBackgroundColor(color)
            setOnClickListener{
                println(curProduct.productName)
                val d = ModifyProdDialog(context, productAdapter , position)
                d.show()
            }
            setOnLongClickListener{
                removalDialog(this.context , curProduct , position)
                true
            }
        }
        updateCounter(0);
    }

    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }

    private fun removalDialog(context: Context, product: Product , position: Int)  {
        val builder = AlertDialog.Builder(context)

        val binding: RemovalDialogBinding = RemovalDialogBinding.inflate(LayoutInflater.from(context))

        builder.setView(binding.root)
        val dialog: AlertDialog = builder.create()

        binding.removalTv.text = buildString {
            append("Are You sure that you want to remove: ")
            append(product.productName)
        }

        binding.noButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.yesButton.setOnClickListener{
            removeProduct(product , position )
            dialog.dismiss()
        }

        dialog.show()
    }



    fun getProduct(position: Int): Product {
        return products[position]
    }

    fun addProduct(product: Product) {
        mainActivity.addProduct(product)

       if (filter.prodMatchesCriteria(product)) {
           val index = products.binarySearch { it.expirationDate.compareTo(product.expirationDate) }
           val insertionIndex = if (index < 0) -(index + 1) else index
           products.add(insertionIndex, product)
           notifyItemRangeChanged(insertionIndex, products.size - 1)
           updateCounter(product.quantity ?:0 )
       }
    }

    fun modifyProduct(product: Product , position: Int  )
    {
        var dif =0;
        products[position].apply {
            val npq = product.quantity ?:0
            val opq = quantity ?:0
            dif = npq-opq
                productName = product.productName
            expirationDate = product.expirationDate
            category = product.category
            quantity = product.quantity
            updateCounter(dif)
        }
        mainActivity.updateTotalCounter(dif)
        notifyItemChanged(position)
    }


    override fun getItemCount(): Int {
        return products.size
    }

    fun updateCounter(modifier: Int)
    {
        _itemQuantityCounter += modifier
        mainActivity.updateFilteredCounter()
        println(_itemQuantityCounter)
    }

     fun getDisplayedProductsTotalQuantity(): Int
    {
        var sum =0
        products.forEach { prod ->
            sum += prod.quantity ?: 0
        }
        return sum
    }

    private fun removeProduct(product: Product, position: Int) {

        var count = product.quantity?:0
        mainActivity.removeProduct(product)
        this.notifyItemRemoved(position)
        updateCounter(count*-1)
    }


}
