package com.example.dima.imagesorter

import android.content.Context

interface BaseView<T> {
    var presenter: T

    fun getContext(): Context
}