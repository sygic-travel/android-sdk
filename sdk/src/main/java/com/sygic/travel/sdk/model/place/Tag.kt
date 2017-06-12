package com.sygic.travel.sdk.model.place

import com.google.gson.annotations.SerializedName

class Tag {

    @SerializedName("key")
    var key: String? = null

    @SerializedName("name")
    var name: String? = null


    override fun equals(other: Any?): Boolean {
        if (other != null && other is Tag) {
            return key == other.key
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        return key!!.hashCode()
    }

}
