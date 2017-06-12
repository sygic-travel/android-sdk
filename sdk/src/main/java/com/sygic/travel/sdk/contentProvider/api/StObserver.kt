package com.sygic.travel.sdk.contentProvider.api

import com.sygic.travel.sdk.api.responseWrappers.StResponse
import com.sygic.travel.sdk.api.responseWrappers.StResponse.Companion.STATUS_OK
import retrofit2.adapter.rxjava.Result
import rx.Observer
import java.io.IOException
import java.util.*

/**
 *
 * Observer which subscribes to receive a response from API.
 * @param <RT> Response type - must be one of the response classes extending [StResponse].
</RT> */
class StObserver<RT : StResponse>
/**
 * @param userCallback Callback, whose methods are called, when the response is processed.
 * *
 * @param multipleCallsMerged Flag indicating whether the Observer hes been subscribed to more
 * *                            than 1 request.
 */
(
        private val userCallback: Callback<RT>,
        private val multipleCallsMerged: Boolean
) : Observer<Result<RT>> {

    private var stResponse: RT? = null
    private val stResponses = ArrayList<RT>()

    /**
     *
     * All API requests have been finished - not necessarily successfully. However an error could
     * have occurred, so the response must be checked.
     */
    override fun onCompleted() {
        if (stResponse?.statusCode == STATUS_OK) {
            userCallback.onSuccess(stResponse!!)
        }
    }

    /**
     *
     * A critical error occurred.
     */
    override fun onError(e: Throwable) {
        userCallback.onFailure(e)
    }

    /**
     *
     * A single API request has been finished.
     */
    override fun onNext(stResponseResult: Result<RT>) {
        if (multipleCallsMerged) {
            if (!isError(stResponseResult)) {
                stResponses.add(stResponseResult.response().body())
            }
        } else {
            if (isError(stResponseResult)) {
                userCallback.onFailure(Exception(getErrorMessage(stResponseResult)))
            } else {
                stResponse = stResponseResult.response().body()
            }
        }
    }

    /**
     *
     * This method is called only if an error has occurred. An API result is passed as an argument
     * and an error message is created.
     * @param stResponseResult Result from of an API request.
     * *
     * @return An error message.
     */
    private fun getErrorMessage(stResponseResult: Result<RT>): String {
        val error = StringBuilder("Error: ")
        if (stResponseResult.response() != null && stResponseResult.response().body() != null) {
            error.append(stResponseResult.response().body().statusCode)
            error.append(": ")
            error.append(stResponseResult.response().body().error?.id)
        } else if (stResponseResult.response().errorBody() != null) {
            try {
                error.append(stResponseResult.response().errorBody().string())
            } catch (e: IOException) {
                error.append(stResponseResult.response().errorBody().toString())
            }

        } else if (stResponseResult.isError) {
            error.append(stResponseResult.error().message)
        }
        return error.toString()
    }

    /**
     *
     * Determines if an error occurred while requesting API.
     * @param stResponseResult Result from of an API request.
     * *
     * @return `true` if an error occurred, `false` otherwise.
     */
    private fun isError(stResponseResult: Result<RT>): Boolean {
        if (stResponseResult.isError) {
            return true
        } else {
            if (stResponseResult.response().errorBody() != null) {
                return true
            } else if (stResponseResult.response().body() != null) {
                return stResponseResult.response().body().statusCode != STATUS_OK
            } else {
                return true
            }
        }
    }
}
