package knight

/**
 * The boolean value within Knight.
 */
class Bool(bool: Boolean) : Idempotent<Boolean>(bool) {
	override fun dump() = print("Boolean($data)")

	/**
	 * Returns either `1L` or `0L` depending on whether `this` is true or false.
	 */
	override fun toLong() = if (data) 1L else 0L

	/**
	 * Returns either `"true"` or `"false"` depending on whether is true or false.
	 */
	override fun toString() = "" + data

	/**
	 * Simply returns the associated boolean.
	 */
	override fun toBoolean() = data

	/**
	 * Converts [other] to a boolean and compares it against `data`.
	 */
	override operator fun compareTo(other: Value) = toLong().compareTo(if (other.toBoolean()) 1L else 0L)
}
