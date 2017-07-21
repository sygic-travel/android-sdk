package com.sygic.travel.sdk.api

abstract class Callback<in T> {
    abstract fun onSuccess(data: T)
    abstract fun onFailure(t: Throwable)
}
