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
		if (rewound) {
			rewound = false
		} else {
			prev = iter.next()
		}

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
			'#' -> {
				takeWhile { it != '\n' } // ignore the line
				parse()
			}

			in " \t\r\n:(){}[]" -> {
				next() // drop the whitespace
				parse()
			}

			in '0'..'9' -> Number(takeWhile(Char::isDigit).toLong())

			in 'a'..'z', '_' -> Variable.fetch(takeWhile { it.isLowerCase() || it.isDigit() || it == '_' })

			'N', 'T', 'F' -> {
				dropKeyword()

				if (c == 'N') Null()
				else Bool(c == 'T')
			}

			'\'', '\"' -> {
				next()
				val string = takeWhile { it != c }

				if (string.lastOrNull() == c)
					throw RuntimeException("Unterimanted quote found. stream: ${string.take(20)}")

				Text(string.dropLast(1))
			}

			else -> {
				// val func = Func.fetch(next()) ?: throw RuntimeException("Unknown token start '$c'")

				// if (c.isUpperCase()) dropKeyword()

				error("unknown token start '$c'")
			}
		}
}

// 				Value[] args = new Value[func.getArity()];

// 				for (int i = 0; i < args.length; ++i) {
// 					args[i] = parse(stream);

// 					if (args[i] == null) {
// 						throw new ParseException("Couldn't parse argument " + i + " for function " + func.getName());
// 					}
// 				}

// 				return new Func(args, func);
// 		}
// 	}

}