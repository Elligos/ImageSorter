package com.example.dima.imagesorter.providers
import io.reactivex.Completable
import io.reactivex.Observable

interface PreferenceHelper {

//    fun getItemsPooling(): Int?
//
//    fun setItemsPooling(poolingType: Int?)

    fun setItemsPooling(poolingType: Int?): Completable

    fun getItemsPooling(): Observable<Int?>

    fun clear(): Completable
}