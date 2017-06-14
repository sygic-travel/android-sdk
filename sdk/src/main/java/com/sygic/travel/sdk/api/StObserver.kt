package com.sygic.travel.sdk.api

import com.sygic.travel.sdk.api.responseWrappers.StResponse
import retrofit2.adapter.rxjava.Result

/**
 *
 * Observer which subscribes to receive a response from API.
 * @param <RT> Response type - must be one of the response classes extending [StResponse].
</RT> */
internal class StObserver<RT : com.sygic.travel.sdk.api.responseWrappers.StResponse>
/**
 * @param userCallback Callback, whose methods are called, when the response is processed.
 * *
 * @param multipleCallsMerged Flag indicating whether the Observer hes been subscribed to more
 * *                            than 1 request.
 */
(
        private val userCallback: Callback<RT>,
        private val multipleCallsMerged: Boolean
) : rx.Observer<Result<RT>> {

    private var stResponse: RT? = null
    private val stResponses = java.util.ArrayList<RT>()

    /**
     *
     * All API requests have been finished - not necessarily successfully. However an error could
     * have occurred, so the response must be checked.
     */
    override fun onCompleted() {
        if (stResponse?.statusCode == com.sygic.travel.sdk.api.responseWrappers.StResponse.Companion.STATUS_OK) {
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
    override fun onNext(stResponseResult: retrofit2.adapter.rxjava.Result<RT>) {
        val body = stResponseResult.response().body()
        if (multipleCallsMerged) {
            if (!isError(stResponseResult)) {
                stResponses.add(body)
            }
        } else {
            if (isError(stResponseResult)) {
                userCallback.onFailure(Exception(getErrorMessage(stResponseResult)))
            } else {
                stResponse = body
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
    private fun getErrorMessage(stResponseResult: retrofit2.adapter.rxjava.Result<RT>): String {
        val error = StringBuilder("Error: ")
        val response = stResponseResult.response()
        val body = response.body()

        if (response != null && body != null) {
            error.append(body.statusCode)
            error.append(": ")
            error.append(body.error?.id)
        } else {
            val errorBody = response.errorBody()
            if (errorBody != null) {
                try {
                    error.append(errorBody)
                } catch (e: java.io.IOException) {
                    error.append(errorBody)
                }

            } else if (stResponseResult.isError) {
                error.append(stResponseResult.error().message)
            }
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
    private fun isError(stResponseResult: retrofit2.adapter.rxjava.Result<RT>): Boolean {
        if (stResponseResult.isError) {
            return true
        } else {
            val response = stResponseResult.response()
            if (response.errorBody() != null) {
                return true
            } else if (response.body() != null) {
                return response.body().statusCode != com.sygic.travel.sdk.api.responseWrappers.StResponse.Companion.STATUS_OK
            } else {
                return true
            }
        }
    }
}
