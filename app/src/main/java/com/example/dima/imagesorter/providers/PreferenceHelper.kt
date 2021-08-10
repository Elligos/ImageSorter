package com.example.dima.imagesorter.providers

interface PreferenceHelper {

    fun getItemsPooling(): Int?

    fun setItemsPooling(poolingType: Int?)
}