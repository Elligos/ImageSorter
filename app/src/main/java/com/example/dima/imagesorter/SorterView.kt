package com.example.dima.imagesorter

import android.content.Context
import com.example.dima.imagesorter.items.RowItem

abstract class SorterView(private val context : Context) : SorterContract.View {
    override var presenter: SorterContract.Presenter
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun getContext(): Context {
        return context
    }
}