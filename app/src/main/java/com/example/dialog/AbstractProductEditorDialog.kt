package com.example.dialog

import android.R
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.databinding.DialogProductBinding
import com.example.utils.CATEGORY
import com.example.domain.Product
import java.time.LocalDate

abstract class AbstractProductEditorDialog(
    context: Context,

) : Dialog(context) {

    protected lateinit var dialogBinding: DialogProductBinding

    abstract fun handleSubmit(product: Product)

     override fun onCreate(savedInstanceState: Bundle? ) {

        dialogBinding = DialogProductBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        val categoriesArr = CATEGORY.values().map { it.name }
        val categoriesAdapter = ArrayAdapter(context.applicationContext, R.layout.simple_spinner_item, categoriesArr)

        categoriesAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        dialogBinding.categorySpinner.adapter = categoriesAdapter


        dialogBinding.selectDateButton.setOnClickListener() {
            creatingDatePicker()
        }

        dialogBinding.CancelButton.setOnClickListener{
            this.dismiss()
        }

         dialogBinding.submitButton.setOnClickListener{
             this.submit()
         }

    }


     fun submit()
    {

        var selectedDate: LocalDate? = null
        var  prodName = dialogBinding.productNameInput.text.toString().trim()
        var category = CATEGORY.valueOf(dialogBinding.categorySpinner.selectedItem.toString())
        var quantityStr = dialogBinding.productQuatitytyIput.text.toString() ;

        println("pd: ${prodName.isEmpty()}")

        println("cat: $category")
        println("quant: ${quantityStr.isEmpty()}")

        var inputCorrect = true

        if(prodName.isEmpty())
        {
            dialogBinding.productNameInput.hint = "The name should not be empty!!!"
            inputCorrect = false
        }
        val dateText = dialogBinding.selectedDateOutput.text.toString().trim()
        println(dateText)
        val regexPattern = "\\d{1,2}-\\d{1,2}-\\d{4}"

        if (!dateText.matches(Regex(regexPattern))) {
            dialogBinding.selectedDateOutput.text ="The expiration date should be selected!!!"
            inputCorrect = false
        }else{
            val dateParts = dateText.split("-")
            selectedDate = LocalDate.of( dateParts[2].toInt() , dateParts[1].toInt() , dateParts[0].toInt()  )
        }
        if (category==null){
            // TODO: implement
            inputCorrect = false
        }
        if(inputCorrect)
        {
            var prodFromInput = Product(prodName , selectedDate!!, category , quantityStr.toIntOrNull())
            this.handleSubmit(prodFromInput)
            this.dismiss()
        }

    }


    private fun creatingDatePicker() {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this.context,
            { _, year, monthOfYear, dayOfMonth ->
                val humanFormat = monthOfYear +1
                dialogBinding.selectedDateOutput.text = "$dayOfMonth-$humanFormat-$year"
                println("dateSelected")
                println("$dayOfMonth/$monthOfYear/$year")
            },
            year,
            month,
            dayOfMonth
        )

        val minDateMillis = calendar.timeInMillis

        datePickerDialog.datePicker.minDate = minDateMillis + 24 * 1000 *60 * 60

        datePickerDialog.show()
    }



}