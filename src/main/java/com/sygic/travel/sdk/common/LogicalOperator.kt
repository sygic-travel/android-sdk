package com.sygic.travel.sdk.common

enum class LogicalOperator constructor(
	val apiExpression: String
) {
	ALL(","),
	ANY("|"),
}
