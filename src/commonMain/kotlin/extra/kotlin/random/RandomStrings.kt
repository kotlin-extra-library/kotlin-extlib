package extra.kotlin.random

import extra.kotlin.util.UUID
import kotlin.random.Random

/**
 * Generates a UUIDv4.
 * @param useUppercase Uses uppercase letters instead.
 * @return The randomly generated UUID string.
 */
@Deprecated("Use UUIDs directly instead.", replaceWith = ReplaceWith("UUID.uuid4().toString()", "extra.kotlin.util.UUID"))
fun Random.uuid(useUppercase: Boolean = false) = UUID.uuid4().toString().let{
    if(useUppercase) it.toUpperCase()
    else it
}

/**
 * A random string containing symbols, letters and numbers. Default length is random between 15 and 20 included.
 * @param length The desired lenght of the string.
 * @return The randomly generated string.
 */
fun Random.nextString(length: Int = nextInt(15, 21)) = buildString {
    for(i in 0 until length){
        append(nextChar())
    }
}

/**
 * A random string containing letters and numbers. Default length is random between 15 and 20 included.
 * @param length The desired lenght of the string.
 * @return The randomly generated string.
 */
fun Random.nextAlphanumericString(length: Int = nextInt(15, 21)) = buildString {
    for(i in 0 until length){
        append(when (nextInt(1, 4)){
            1 -> nextNumericChar()
            2 -> nextLowercaseLetter()
            else -> nextUppercaseLetter()
        })
    }
}

/**
 * A random character from `a` to `f` included or a number.
 * @param useUppercase Use uppercase letters instead.
 * @return The randomly generated character.
 */
fun Random.nextHexChar(useUppercase: Boolean = false) = when (nextInt(1, 3)){
    1 -> nextNumericChar()
    else -> if(useUppercase) nextUppercaseLetter(0, 6) else nextLowercaseLetter(0, 6)
}

/**
 * A random character between symbols, letters and numbers.
 * @return The randomly generated character.
 */
fun Random.nextChar() = nextInt(33, 127).toChar()

/**
 * A random character between `0` and '9' included.
 * @param from Starting letter index. Default is 0.
 * @param to Last letter index. Default is 9.
 * @return The randomly generated character.
 */
fun Random.nextNumericChar(from: Int = 0, to: Int = 9): Char {
    if(from < 0 || from > 8 || to < 2 || to > 9)
        throw IllegalArgumentException("Invalid parameters. Select Ranges from 0 included to 9 included")
    return nextInt(48 + from, 58 - (9 - to)).toChar()
}

/**
 * A random character between `a` and 'z' included.
 * @param from Starting letter index. Default is 0.
 * @param to Last letter index. default is 26.
 * @return The randomly generated character.
 */
fun Random.nextLowercaseLetter(from: Int = 0, to: Int = 26): Char{
    if(from < 0 || from > 25 || to < 1 || to > 26)
        throw IllegalArgumentException("Invalid parameters. Select Ranges from 0 included to 26 included")
    return nextInt(97 + from, 123 - (26 - to)).toChar()
}

/**
 * A random character between `A` and 'Z' included.
 * @param from Starting letter index. Default is 0.
 * @param to Last letter index. default is 26.
 * @return The randomly generated character.
 */
fun Random.nextUppercaseLetter(from: Int = 0, to: Int = 26): Char {
    if(from < 0 || from > 26 || to < 1 || to > 26)
        throw IllegalArgumentException("Invalid parameters. Select Ranges from 0 included to 26 included")
    return nextInt(65 + from, 91 - (26 - to)).toChar()
}

/**
 * A random character between `A` to 'Z' included and 'a' to 'z' included.
 * @return The randomly generated character.
 */
fun Random.nextLetter() = when(nextInt(2)){
    0 -> nextLowercaseLetter()
    else -> nextUppercaseLetter()
}


