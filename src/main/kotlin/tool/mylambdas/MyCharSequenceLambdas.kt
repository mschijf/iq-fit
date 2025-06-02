package tool.mylambdas

import java.security.MessageDigest


fun CharSequence.distinct(): String {
    val result = StringBuilder()
    this.forEach { ch -> if (!result.contains(ch)) result.append(ch) }
    return result.toString()
}

/**
 * Returns a substring between two delimeters.
 * If string doesn't contain beforeDelimeter after the afterDelimter, the result is equal to using the substringAfter (with afterDelimeter)
 * If string doesn't contain afterDelimeter, the result is equal to using the substringBefore (with beforeDelimeter)
 * If string doesn't contain both delimeters, the original string is returend
 *
 */
fun String.substringBetween(afterDelimeter: String, beforeDelimter: String): String {
    return this.substringAfter(afterDelimeter).substringBefore(beforeDelimter)
}

fun CharSequence.hasOnlyDigits(): Boolean {
    return this.all{ch -> ch.isDigit()}
}

fun String.toMD5Hexadecimal(): String {
    return MessageDigest.getInstance("MD5").digest(this.toByteArray()).toHexString()
}

private fun ByteArray.toHexString() : String {
    val builder = StringBuilder()

    for (b in this) {
        builder.append(b.toHexString())
    }

    return builder.toString()
}

private val HEX_CHARS = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

private fun Byte.toHexString() : String {
    val thisAsInt = this.toInt()
    val resultChar2 = HEX_CHARS[thisAsInt and 0x0f]
    val resultChar1 = HEX_CHARS[thisAsInt shr 4 and 0x0f]
    return "$resultChar1$resultChar2"
}