package com.github.alexdochioiu.core.di

/**
 * Created by Alex Dochioiu on 2019-05-22
 */
interface InjectableComponent<T> {
    fun inject(parent: T) : T
}