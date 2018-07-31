package com.sygic.travel.sdk.common

enum class LogicalOperator constructor(
	internal val apiOperator: String
) {
	AND(","),
	OR("|"),
}
