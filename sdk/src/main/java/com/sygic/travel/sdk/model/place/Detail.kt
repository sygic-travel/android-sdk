package com.sygic.travel.sdk.model.place

import com.sygic.travel.sdk.model.media.MainMedia

/**
 * Place detailed information.
 */
class Detail {

    var tags: List<Tag>? = null
    var description: Description? = null
    var address: String? = null
    var admission: String? = null
    var duration: Int = 0
    var email: String? = null
    var openingHours: String? = null
    var phone: String? = null
    var mainMedia: MainMedia? = null
    var references: List<Reference>? = null

}
