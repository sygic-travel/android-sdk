package com.sygic.travel.sdk.contentProvider.api

abstract class Callback<in T> {
    abstract fun onSuccess(data: T)
    abstract fun onFailure(t: Throwable)
}
