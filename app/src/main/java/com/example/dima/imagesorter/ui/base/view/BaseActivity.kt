package com.example.dima.imagesorter.ui.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity(), MVPView, BaseFragment.CallBack {

    override fun onCreate(savedInstanceState : Bundle?){
        performDI()
        super.onCreate(savedInstanceState)
    }

    private fun performDI() = AndroidInjection.inject(this)




}