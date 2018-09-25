package com.sygic.travel.sdk.directions.model

import java.io.Serializable
import java.util.Date

class DirectionTime(
	val datetimeLocal: Date?,
	val datetime: Date?
) : Serializable
