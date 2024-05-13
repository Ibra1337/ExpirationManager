package com.example.dialog

import android.R
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.MainActivity
import com.example.databinding.FilterDialogBinding
import com.example.domain.IMainActivity
import com.example.utils.CATEGORY
import com.example.utils.Filter

class FilterDialog(
    context: Context ,
    private var mainActivity: IMainActivity

)  :Dialog(context){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = FilterDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoriesArr = arrayOf("-none-", "FOOD", "MEDICINE", "COSMETIC")

        val expiredArr = arrayOf("-none-" , "Yes" , "No")

        val categoriesAdapter = ArrayAdapter(context.applicationContext ,  R.layout.simple_spinner_item, categoriesArr )
        categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        val expiredAdapter = ArrayAdapter(context.applicationContext , R.layout.simple_spinner_item , expiredArr)
        expiredAdapter.setDropDownViewResource(R.layout.simple_spinner_item)

        binding.selectCategotySpinner.adapter = categoriesAdapter

        binding.isExpiredSpinner.adapter = expiredAdapter

        binding.cancelButton.setOnClickListener{
                dismiss()
        }

        val translateFromStrToBoolean: MutableMap<String, Boolean?> = HashMap()
        translateFromStrToBoolean["-none-"] = null
        translateFromStrToBoolean["Yes"] = true
        translateFromStrToBoolean["No"] = false


        binding.filetrConfirmButton.setOnClickListener{
            var filterCategory :CATEGORY? = null
            var isExpired: Boolean? = null

            if (binding.selectCategotySpinner.selectedItemId != 0.toLong())
                filterCategory = CATEGORY.valueOf(binding.selectCategotySpinner.selectedItem.toString())

            isExpired = translateFromStrToBoolean[binding.isExpiredSpinner.selectedItem.toString()]
            val filter = Filter(filterCategory , isExpired)

            mainActivity.filter(filter)
            println(filter)
            dismiss()
        }

        println("created")
    }


}
