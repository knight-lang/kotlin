package knight

/**
 * The string value within Knight.
 */
class Text(data: String) : Idempotent<String>(data) {
	override fun dump() = print("String($data)")

	/**
	 * Simply returns [data].
	 */
	override fun toString() = data

	/**
	 * Returns whether [data] is empty.
	 */
	override fun toBoolean() = !data.isEmpty()

	/**
	 * Converts [data] to a `Long`, as per the Knight specs.
	 *
	 * In essence, leading whitespace is stripped, and then an optional `-` or `+` is allowed, after which as many digits
	 * as possible are read---if no valid digits are found, the result is zero.
	 */
	override fun toLong(): Long {
		var txt = data

		while (!txt.isEmpty() && txt[0].isWhitespace())
			txt = txt.substring(1)

		if (txt.isEmpty())
			return 0L

		val isNegative = txt[0] == '-'
		if (isNegative || txt[0] == '+')
			txt = txt.substring(1)

		var result = 0L

		while (!txt.isEmpty() && txt[0].isDigit()) {
			result = result * 10L + (txt[0].toLong() - 0x30L)
			txt = txt.substring(1)
		}

		return result * if (isNegative) -1 else 1;
	}

	/**
	 * Converts [other] to a String and it compares against [data], lexicographically.
	 */
	override operator fun compareTo(other: Value) = data.compareTo(other.toString())

	/**
	 * Converts [other] to a String and then data [data] with it.
	 */
	override operator fun plus(other: Value) = Text(data + other.toString())

	/**
	 * Replicates [data] [other] times (when converted to a `Long`).
	 *
	 * @throws RunException Thrown if the `Long` representation of [other] is negative.
	 */
	override operator fun times(other: Value): Value {
		val amnt = other.toLong()
		var content = ""

		if (amnt < 0)
			throw RunException("string multiplication by a negative number")

		for (_i in 1..amnt)
			content += data

		return Text(content)
	}
}
