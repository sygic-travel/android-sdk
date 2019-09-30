package com.sygic.travel.sdk.directions.model

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import java.io.Serializable

class DirectionTime constructor(
	val datetimeLocal: LocalDateTime,
	val datetime: Instant
) : Serializable
