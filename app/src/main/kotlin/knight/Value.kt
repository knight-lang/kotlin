package knight
import knight.Stream

typealias UOE = UnsupportedOperationException

interface Value : Comparable<Value> {
	abstract fun run(): Value
	abstract fun dump()

	abstract fun toLong(): Long
	override abstract fun toString(): String
	abstract fun toBoolean(): Boolean

	override operator fun compareTo(other: Value): Int = throw UOE("cannot compare $this")

	operator fun not(): Boolean = !toBoolean()
	operator fun plus(other: Value): Value = throw UOE("cannot add $this")
	operator fun minus(other: Value): Value = throw UOE("cannot subtract $this")
	operator fun times(other: Value): Value = throw UOE("cannot multiply $this")
	operator fun div(other: Value): Value = throw UOE("cannot divide $this")
	operator fun rem(other: Value): Value = throw UOE("cannot modulo $this")
	   infix fun pow(other: Value): Value = throw UOE("cannot exponentiate $this")
}


private fun Stream.dropKeyword() = takeWhile { it.isUpperCase() || it == '_' }
private fun Char.isIdentifierBody() = isLowerCase() || isDigit() || this == '_' 

// fun parse(string: String): Value? = parse(Stream(string))
fun parse(stream: Stream): Value? {
	val c = stream.peek() ?: return null

	return when (c) {
		'#' -> stream.takeWhile { it != '\n' }.let { parse(stream) }

		in " \t\r\n:(){}[]" ->
			stream
				.next() // ignore the whitespace
				.let { parse(stream) }

		in "0123456789" -> stream.takeWhile(Char::isDigit).toLong().let(::Number)
		in "abcdefghijklmnopqrstuvwxyz_" -> stream.takeWhile(Char::isIdentifierBody).let(Variable::fetch)
		in "TFN" -> stream.dropKeyword().let { if (c == 'N') Null() else Bool(c == 'T') }
		in "\'\"" ->
			stream
				.also(Stream::next) // ignore the starting quote
				.takeWhile { it != c }
				.let { 
					if (it.lastOrNull() == c)
						error("Unterimanted quote found. stream: ${it.take(20)}")
					Text(it.dropLast(1))
				}


		else -> {
			// val func = Func.
			error("unknown token start '$c'")
		}
	}
}

// 			default:
// 				FuncInner func = Func.fetch(c);

// 				if (func == null) {
// 					throw new ParseException("Unknown token start '" + c + "'");
// 				}

// 				if (Character.isUpperCase(c)) {
// 					while (stream.hasNext()) {
// 						if (!Character.isUpperCase(c = stream.next()) && c != '_') {
// 							stream.rewind();
// 							break;
// 						}
// 					}
// 				}

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

// 	public static Value run(String input) throws RuntimeException, ParseException {
// 		Value value = parse(new Stream(input));

// 		if (value == null)
// 			throw new ParseException("Couldn't parse anything from the stream");

// 		return value.run();
// 	}

// 	public static void main(String[] args) {
// 		if (args.length != 2 || (!args[0].equals("-e") && !args[0].equals("-f"))) {
// 			System.err.println("usage: knight (-e 'program' | -f file)");
// 			System.exit(1);
// 		}

// 		try {
// 			if (args[0].equals("-e")) {
// 				run(args[1]);
// 			} else {
// 				// Stream _s = new Stream(Files.readAllBytes(Paths.get("file")));

// 				run(new String(Files.readAllBytes(Paths.get(args[1])), StandardCharsets.US_ASCII));
// 			}
// 		} catch (Exception err) {
// 			System.err.println("error occured: " + err);
// 			err.printStackTrace();
// 			System.exit(1);
// 		}
// 	}
// }
