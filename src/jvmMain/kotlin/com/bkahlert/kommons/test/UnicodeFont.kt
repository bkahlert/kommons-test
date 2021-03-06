package com.bkahlert.kommons.test

import kotlin.streams.asSequence

internal enum class UnicodeFont(
    private val capitalLetters: IntArray,
    private val smallLetters: IntArray,
    private val digits: IntArray? = null,
) {
    Bold("๐".."๐", "๐".."๐ณ", "๐".."๐"),
    Italic(("๐ด".."๐").toIntArray(), "๐๐๐๐๐๐๐โ๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง".codePoints),
    BoldItalic("๐จ".."๐", "๐".."๐"),
    Script("๐โฌ๐๐โฐโฑ๐ขโโ๐ฅ๐ฆโโณ๐ฉ๐ช๐ซ๐ฌโ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต".codePoints, "๐ถ๐ท๐ธ๐นโฏ๐ปโ๐ฝ๐พ๐ฟ๐๐๐๐โด๐๐๐๐๐๐๐๐๐๐๐".codePoints),
    BoldScript("๐".."๐ฉ", "๐ช".."๐"),
    Fraktur("๐๐โญ๐๐๐๐โโ๐๐๐๐๐๐๐๐โ๐๐๐๐๐๐๐โจ".codePoints, ("๐".."๐ท").toIntArray()),
    BoldFraktur("๐ฌ".."๐", "๐".."๐"),
    DoubleStruck("๐ธ๐นโ๐ป๐ผ๐ฝ๐พโ๐๐๐๐๐โ๐โโโ๐๐๐๐๐๐๐โค".codePoints, ("๐".."๐ซ").toIntArray(), ("๐".."๐ก").toIntArray()),
    SansSerif("๐ ".."๐น", "๐บ".."๐", "๐ข".."๐ซ"),
    SansSerifBold("๐".."๐ญ", "๐ฎ".."๐", "๐ฌ".."๐ต"),
    SansSerifItalic("๐".."๐ก", "๐ข".."๐ป"),
    SansSerifBoldItalic("๐ผ".."๐", "๐".."๐ฏ"),
    Monospace("๐ฐ".."๐", "๐".."๐ฃ", "๐ถ".."๐ฟ"),
    ;

    init {
        check(capitalLetters.size == 26) { "26 capital letters needed" }
        check(smallLetters.size == 26) { "26 small letters needed" }
        digits?.also { check(it.size == 10) { "either no or 10 digits needed" } }
    }

    constructor(capitalLetters: IntRange, smallLetters: IntRange, digits: IntRange? = null) : this(
        capitalLetters.toIntArray(), smallLetters.toIntArray(), digits?.toIntArray(),
    )

    fun convertCodePointOrNull(codePoint: Int): Int? =
        capitalLetters.getOrNull(codePoint - capitalLetterACodePoint)
            ?: smallLetters.getOrNull(codePoint - smallLetterACodePoint)
            ?: digits?.getOrNull(codePoint - digitZeroCodePoint)

    fun format(text: String, onFailure: (codePoint: Int) -> Int = { it }): String {
        return buildString {
            text.codePoints().forEach {
                val codePoint = convertCodePointOrNull(it) ?: onFailure(it)
                appendCodePoint(codePoint)
            }
        }
    }

    companion object {
        const val capitalLetterACodePoint = 'A'.code
        const val smallLetterACodePoint = 'a'.code
        const val digitZeroCodePoint = '0'.code
    }
}

private val String.codePoints: IntArray get() = codePoints().asSequence().toList().toIntArray()
private val String.codePoint: Int get() = codePoints.single()
private operator fun String.rangeTo(other: String): IntRange = codePoint..other.codePoint
private fun IntRange.toIntArray(): IntArray = toList().toIntArray()
