package com.bkahlert.kommons.test

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class UnicodeFontTest {

    @Test fun format() = test {
        UnicodeFont.Bold.format(capitalLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.Italic.format(capitalLetters) shouldBe "๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.BoldItalic.format(capitalLetters) shouldBe "๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐"
        UnicodeFont.Script.format(capitalLetters) shouldBe "๐โฌ๐๐โฐโฑ๐ขโโ๐ฅ๐ฆโโณ๐ฉ๐ช๐ซ๐ฌโ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต"
        UnicodeFont.BoldScript.format(capitalLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ"
        UnicodeFont.Fraktur.format(capitalLetters) shouldBe "๐๐โญ๐๐๐๐โโ๐๐๐๐๐๐๐๐โ๐๐๐๐๐๐๐โจ"
        UnicodeFont.BoldFraktur.format(capitalLetters) shouldBe "๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐"
        UnicodeFont.DoubleStruck.format(capitalLetters) shouldBe "๐ธ๐นโ๐ป๐ผ๐ฝ๐พโ๐๐๐๐๐โ๐โโโ๐๐๐๐๐๐๐โค"
        UnicodeFont.SansSerif.format(capitalLetters) shouldBe "๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น"
        UnicodeFont.SansSerifBold.format(capitalLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ"
        UnicodeFont.SansSerifItalic.format(capitalLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐ ๐ก"
        UnicodeFont.SansSerifBoldItalic.format(capitalLetters) shouldBe "๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.Monospace.format(capitalLetters) shouldBe "๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐๐๐๐๐"

        UnicodeFont.Bold.format(smallLetters) shouldBe "๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ"
        UnicodeFont.Italic.format(smallLetters) shouldBe "๐๐๐๐๐๐๐โ๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง"
        UnicodeFont.BoldItalic.format(smallLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.Script.format(smallLetters) shouldBe "๐ถ๐ท๐ธ๐นโฏ๐ปโ๐ฝ๐พ๐ฟ๐๐๐๐โด๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.BoldScript.format(smallLetters) shouldBe "๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐"
        UnicodeFont.Fraktur.format(smallLetters) shouldBe "๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท"
        UnicodeFont.BoldFraktur.format(smallLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.DoubleStruck.format(smallLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ"
        UnicodeFont.SansSerif.format(smallLetters) shouldBe "๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.SansSerifBold.format(smallLetters) shouldBe "๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ๐๐๐๐๐๐๐๐"
        UnicodeFont.SansSerifItalic.format(smallLetters) shouldBe "๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต๐ถ๐ท๐ธ๐น๐บ๐ป"
        UnicodeFont.SansSerifBoldItalic.format(smallLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ๐ฌ๐ญ๐ฎ๐ฏ"
        UnicodeFont.Monospace.format(smallLetters) shouldBe "๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐๐ ๐ก๐ข๐ฃ"

        UnicodeFont.Bold.format(digits) shouldBe "๐๐๐๐๐๐๐๐๐๐"
        UnicodeFont.Italic.format(digits) shouldBe digits
        UnicodeFont.BoldItalic.format(digits) shouldBe digits
        UnicodeFont.Script.format(digits) shouldBe digits
        UnicodeFont.BoldScript.format(digits) shouldBe digits
        UnicodeFont.Fraktur.format(digits) shouldBe digits
        UnicodeFont.BoldFraktur.format(digits) shouldBe digits
        UnicodeFont.DoubleStruck.format(digits) shouldBe "๐๐๐๐๐๐๐๐๐ ๐ก"
        UnicodeFont.SansSerif.format(digits) shouldBe "๐ข๐ฃ๐ค๐ฅ๐ฆ๐ง๐จ๐ฉ๐ช๐ซ"
        UnicodeFont.SansSerifBold.format(digits) shouldBe "๐ฌ๐ญ๐ฎ๐ฏ๐ฐ๐ฑ๐ฒ๐ณ๐ด๐ต"
        UnicodeFont.SansSerifItalic.format(digits) shouldBe digits
        UnicodeFont.SansSerifBoldItalic.format(digits) shouldBe digits
        UnicodeFont.Monospace.format(digits) shouldBe "๐ถ๐ท๐ธ๐น๐บ๐ป๐ผ๐ฝ๐พ๐ฟ"
    }

    @Test fun format_throwing() = test {
        UnicodeFont.Bold.format(digits) shouldBe "๐๐๐๐๐๐๐๐๐๐"
        shouldThrow<IllegalArgumentException> { UnicodeFont.Italic.format(digits) { throw IllegalArgumentException("cannot format $it") } }
    }
}

private const val capitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val smallLetters = "abcdefghijklmnopqrstuvwxyz"
private const val digits = "0123456789"
