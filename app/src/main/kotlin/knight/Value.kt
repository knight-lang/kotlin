package knight
import knight.Stream

class BadType(message: String) : KnightException(message)
// typealias UOE = UnsupportedOperationException

interface Value : Comparable<Value> {
	abstract fun run(): Value
	abstract fun dump()

	abstract fun toLong(): Long
	override abstract fun toString(): String
	abstract fun toBoolean(): Boolean

	override operator fun compareTo(other: Value): Int = throw BadType("cannot compare $this")

	operator fun not(): Boolean = !toBoolean()
	operator fun plus(other: Value): Value = throw BadType("cannot add $this")
	operator fun minus(other: Value): Value = throw BadType("cannot subtract $this")
	operator fun times(other: Value): Value = throw BadType("cannot multiply $this")
	operator fun div(other: Value): Value = throw BadType("cannot divide $this")
	operator fun rem(other: Value): Value = throw BadType("cannot modulo $this")
	   infix fun pow(other: Value): Value = throw BadType("cannot exponentiate $this")
}
