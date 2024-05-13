package com.example.domain

import android.text.Layout
import android.view.LayoutInflater
import com.example.utils.Filter

interface IMainActivity {

    fun addProduct(product: Product)



    fun updateFilteredCounter()

    fun getLayoutInflation(): LayoutInflater


    fun updateTotalCounter(modifier: Int)

    fun removeProduct(product: Product)

    fun filter(filter: Filter)



}