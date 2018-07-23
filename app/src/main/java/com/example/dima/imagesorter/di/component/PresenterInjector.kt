package com.example.dima.imagesorter.di.component

import com.example.dima.imagesorter.SorterContract
import com.example.dima.imagesorter.SorterPresenter
import com.example.dima.imagesorter.di.module.ContextModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(ContextModule::class)])
interface PresenterInjector {


    fun inject(sorterPresenter: SorterPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun sorterView(sorterView: SorterContract.View): Builder
    }
}