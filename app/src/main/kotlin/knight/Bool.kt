package knight

import knight.Value

class Bool(val bool: Boolean) : Value {
	override fun run() = this
	override fun dump() = print("Boolean($bool)")

	override fun toLong()    = if (bool) 1L else 0L
	override fun toString()  = "" + bool
	override fun toBoolean() = bool

	override operator fun compareTo(other: Value) = bool.compareTo(other.toBoolean())
	override fun equals(other: Any?) = other is Bool && bool == other.bool
}
