package com.gkgio.borsch_cooker.utils

import io.reactivex.subjects.PublishSubject

abstract class BaseEvent {
    private var subject = PublishSubject.create<String>()

    fun getEventResult() = subject

    open fun onComplete(event: String) = subject.onNext(event)
}