package knight

open class KnightException(message: String) : RuntimeException(message)
class ParseException(message: String) : KnightException(message)
class RunException(message: String) : KnightException(message)
