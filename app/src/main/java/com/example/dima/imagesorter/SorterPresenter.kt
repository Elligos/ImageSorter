package com.example.dima.imagesorter


import com.example.dima.imagesorter.di.component.DaggerPresenterInjector
import com.example.dima.imagesorter.di.component.PresenterInjector
import com.example.dima.imagesorter.di.module.ContextModule

class SorterPresenter(val view: SorterContract.View) : SorterContract.Presenter {

    private val injector: PresenterInjector = DaggerPresenterInjector
            .builder()
            .sorterView(view)
            .contextModule(ContextModule)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is SorterPresenter -> injector.inject(this)
        }
    }

    override var currentFiltering: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun loadImages() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByDate() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortBySize() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortBuName() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByType() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}