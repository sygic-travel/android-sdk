package com.sygic.travel.sdk.common

enum class LogicalOperator constructor(
	internal val apiOperator: String
) {
	ALL(","),
	ANY("|"),
	@Deprecated(replaceWith = ReplaceWith("ALL"), message = "use ALL operator")
	AND(","),
	@Deprecated(replaceWith = ReplaceWith("ANY"), message = "use ANY operator")
	OR("|"),
}
