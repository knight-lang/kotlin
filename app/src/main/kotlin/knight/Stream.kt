package knight

class Stream: Iterator<Char> {
	private val iter: Iterator<Char>
	private var prev: Char = '\u0000'
	private var rewound: Boolean = false

	constructor(stream: String): this(stream.iterator())
	constructor(iter: Iterator<Char>) {
		this.iter = iter
	}

	override operator fun hasNext() = rewound || iter.hasNext()
	override operator fun next(): Char {
		if (rewound) rewound = false
		else prev = iter.next()

		return prev
	}

	fun peek() = if (hasNext()) next().also { rewind() } else null
	fun rewind() {
		assert(!rewound)
		rewound = true
	}

	inline fun takeWhile(cond: (Char) -> Boolean): String {
		var collection = ""

		var c: Char

		while (hasNext()) {
			c = next()

			if (cond(c)) {
				collection += c
			} else {
				rewind()
				break
			}
		}

		return collection
	}

	// fun parse(string: String): Value? = parse(Stream(string))
	fun parse(): Value? {
		fun Stream.dropKeyword() = takeWhile { it.isUpperCase() || it == '_' }

		return when (val c = peek() ?: return null) {
			'#' -> takeWhile { it != '\n' }.let { parse() }

			in " \t\r\n:(){}[]" -> next().let { parse() }

			in '0'..'9' -> Number(takeWhile(Char::isDigit).toLong())

			in 'a'..'z', '_' -> Variable.fetch(takeWhile { it.isLowerCase() || it.isDigit() || it == '_' })

			'N', 'T', 'F' -> {
				dropKeyword()

				if (c == 'N') Null()
				else Bool(c == 'T')
			}

			'\'', '\"' -> {
				next() // remove quotex
				val string = takeWhile { it != c }

				if (string.lastOrNull() == c)
					throw ParseException("Unterimanted quote found. stream: ${string.take(20)}")

				next() // remove trailing quote

				Text(string)
			}

			else -> {
				val func = Function.fetch(c) ?: throw ParseException("Unknown token start '$c'")

				if (c.isUpperCase()) dropKeyword()
				else next()

				val args = Array(func.arity) {
					parse() ?: throw ParseException("Missing argument ${it + 1} for func '$func'")
				}

				Ast(func, args)
			}
		}
	}
}
