package com.gkgio.data.base

import com.gkgio.data.exception.ServerExceptionTransformer
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function


abstract class BaseService(private val serverExceptionTransformer: ServerExceptionTransformer) {

    fun <T> executeRequest(single: Single<T>): Single<T> = single.onErrorResumeNext {
        Single.error(serverExceptionTransformer.transform(it))
    }

    fun <T> executeRequest(observable: Observable<T>): Observable<T> = observable.onErrorResumeNext(
        Function { Observable.error(serverExceptionTransformer.transform(it)) }
    )

    fun executeRequest(completable: Completable): Completable = completable.onErrorResumeNext {
        Completable.error(serverExceptionTransformer.transform(it))
    }
}