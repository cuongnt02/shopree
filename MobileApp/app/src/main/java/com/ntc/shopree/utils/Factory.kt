package com.ntc.shopree.utils

interface Factory<T> {
    fun create(): T
}