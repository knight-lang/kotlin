package knight

/**
 * The boolean value within Knight.
 */
class Null : Idempotent<Unit> {
	constructor(): super(Unit)
	override fun dump() = print("Null()")

	/**
	 * Simply returns `0L`.
	 */
	override fun toLong() = 0L

	/**
	 * Simply returns `"null"`.
	 */
	override fun toString() = "null"

	/**
	 * Simply returns `false`.
	 */
	override fun toBoolean() = false
}
