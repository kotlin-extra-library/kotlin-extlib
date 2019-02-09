package extra.kotlin.string

@Suppress("NOTHING_TO_INLINE") inline fun Char.isDigit() = this in '0'..'9'
@Suppress("NOTHING_TO_INLINE") inline fun Char.isLowercase() = this in 'a' .. 'z'
@Suppress("NOTHING_TO_INLINE") inline fun Char.isUppercase() = this in 'A' .. 'Z'
@Suppress("NOTHING_TO_INLINE") inline fun Char.isLetter() = isLowercase() || isUppercase()
@Suppress("NOTHING_TO_INLINE") inline fun Char.isControl() = this < ' '
