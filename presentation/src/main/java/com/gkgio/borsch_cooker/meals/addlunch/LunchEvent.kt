package com.gkgio.borsch_cooker.meals.addlunch

import com.gkgio.borsch_cooker.meals.MealsItemUi
import io.reactivex.subjects.PublishSubject

abstract class LunchEvent {
    private var subject = PublishSubject.create<List<MealsItemUi>>()

    fun getEventResult() = subject

    open fun onComplete(event: List<MealsItemUi>) = subject.onNext(event)
}