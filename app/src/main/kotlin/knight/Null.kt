package knight

import knight.Value

class Null : Value {
	override fun run() = this
	override fun dump() = print("Null()")

	override fun toLong()    = 0L
	override fun toString()  = "null"
	override fun toBoolean() = false

	override fun equals(other: Any?) = other is Null
}
