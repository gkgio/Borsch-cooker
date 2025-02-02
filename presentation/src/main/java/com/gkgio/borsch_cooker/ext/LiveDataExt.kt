package com.gkgio.borsch_cooker.ext

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeValue(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { observer(it) })
}

val <T> LiveData<T>.nonNullValue: T
    get() = value ?: throw IllegalArgumentException("Value can not be null")

fun <T> LiveData<T>.isInitialized() = value != null

fun <T> LiveData<T>.isNonInitialized() = value == null

inline fun <T> LiveData<T>.nonNullObserve(
    @NonNull owner: LifecycleOwner,
    crossinline block: (T) -> Unit
) {
    observe(owner, Observer { value ->
        value?.let { block(it) }
    })
}