package com.example

import com.example.adapter.ProductAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.InMemoryProductRepository
import com.example.data.ProductRepository
import com.example.databinding.ActivityMainBinding
import com.example.dialog.AddingProdDialog
import com.example.dialog.FilterDialog
import com.example.utils.CATEGORY
import com.example.domain.IMainActivity
import com.example.domain.Product
import com.example.utils.Filter
import java.time.LocalDate
import java.util.SortedSet
import java.util.TreeSet

class MainActivity : AppCompatActivity() , IMainActivity {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prodAdapter: ProductAdapter

    private var productRepository: ProductRepository = InMemoryProductRepository()

    private var prodCounter :Int = 0
        get() = field

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        prodAdapter = ProductAdapter( productRepository.asMutableList() , this )

        binding.rvProducts.adapter = prodAdapter

        binding.rvProducts.layoutManager = LinearLayoutManager(this)

        binding.addProductButton.setOnClickListener{
            handleAddProductClicked()
        }

        binding.filterButton.setOnClickListener{
            displayFilterDialog()
        }
    }

    private fun displayFilterDialog() {
        println("filter")
        val d = FilterDialog(this , this)
        d.show()
    }


    private fun initData()
    {
        addProduct(Product("meth" , LocalDate.now().plusDays(50) , CATEGORY.MEDICINE , 4))
        addProduct(Product("patato" , LocalDate.now().plusDays(3) , CATEGORY.COSMETIC , 4))
        addProduct(Product("pizza" , LocalDate.now().minusDays(1) , CATEGORY.FOOD , 2)   )
        addProduct(Product("tomato" , LocalDate.now().plusYears(2)  , CATEGORY.FOOD))
        addProduct(Product("painkiller" , LocalDate.now().minusDays(7) , CATEGORY.MEDICINE))
        addProduct(Product("Steak" , LocalDate.now().plusDays(1) , CATEGORY.FOOD))
        addProduct(Product("soap" , LocalDate.now().plusYears(7) , CATEGORY.COSMETIC))
        addProduct(Product("Cancer drug" , LocalDate.now().plusDays(7) , CATEGORY.MEDICINE))
        addProduct(Product("fresh fries" , LocalDate.now().plusYears(100) , CATEGORY.MEDICINE))
    }



     fun handleAddProductClicked()
    {
         println("Add Prod Clicked !!!")
        var d = AddingProdDialog(this , prodAdapter )
        d.show()
    }

    override fun addProduct(product: Product) {
        productRepository.addProduct(product)
        updateTotalCounter(product.quantity ?:0 )
    }


    override fun filter(filter: Filter) {
        val matchedProducts = productRepository.filter(filter)
        prodAdapter.filter = filter
        prodAdapter.updateProducts(matchedProducts)
    }


    override fun updateFilteredCounter() {
        binding.filteredCountOutput.text = buildString {
            append("filtered count: ")
            append(prodAdapter.itemQuantityCounter)
        }


    }

    override fun getLayoutInflation(): LayoutInflater {
        return this.layoutInflater
    }

    override fun updateTotalCounter(modifier: Int) {
        prodCounter += modifier
        binding.totalCountOutput.text = "total count: $prodCounter"
    }

    override fun removeProduct(product: Product) {
        var dif = product.quantity?:0
        productRepository.removeProduct(product)
        updateTotalCounter(-1*dif)
    }

}
