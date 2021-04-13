package knight

abstract class NonIdempotent : Value {
	final override fun toLong() = run().toLong()
	final override fun toString() = run().toString()
	final override fun toBoolean() = run().toBoolean()

	final override operator fun compareTo(other: Value) = run().compareTo(other)

	final override operator fun plus(other: Value) = run() + other
	final override operator fun minus(other: Value) = run() - other
	final override operator fun times(other: Value) = run() * other
	final override operator fun div(other: Value) = run() / other
	final override operator fun rem(other: Value) = run() % other
	final override    infix fun pow(other: Value) = run() pow other
}
