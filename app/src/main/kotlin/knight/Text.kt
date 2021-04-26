package knight

import knight.Value

class Text(val text: String) : Value {
	override fun run() = this
	override fun dump() = print("String($text)")

	override fun toString() = text
	override fun toBoolean() = !text.isEmpty()
	override fun toLong(): Long {
		var txt = text

		while (!txt.isEmpty() && txt[0].isWhitespace())
			txt = txt.substring(1)

		if (txt.isEmpty()) return 0L

		val isNegative = txt[0] == '-'
		if (isNegative || txt[0] == '+') txt = txt.substring(1)

		var result = 0L

		while (!txt.isEmpty() && txt[0].isDigit()) {
			result = result * 10L + (txt[0].toLong() - 0x30L)
			txt = txt.substring(1)
		}

		return result * if (isNegative) -1 else 1;

	}

	override operator fun compareTo(other: Value) = text.compareTo(other.toString())
	override fun equals(other: Any?) = other is Text && text == other.text

	override operator fun plus(other: Value) = Text(text + other.toString())
	override operator fun times(other: Value): Value {
		val amnt = other.toLong()
		var content = ""

		if (amnt < 0)
			throw RunException("string multiplication by a negative number")

		for (_i in 1..amnt)
			content += text

		return Text(content)
	}
}
