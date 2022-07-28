package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.com.bkahlert.kommons.emptyString
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.displayNameFor
import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.collections.Map.Entry
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty
import kotlin.streams.asStream


class DynamicTestDisplayNameGeneratorTest {

    @Suppress("SpellCheckingInspection")
    @Test fun display_name_for() = testAll {
        displayNameFor(nullSubject) shouldBe "𝘯𝘶𝘭𝘭"
        displayNameFor(kPropertySubject) shouldBe "ᴩʀᴏᴩᴇʀᴛy length"
        displayNameFor(kFunctionSubject) shouldBe "ꜰᴜɴᴄᴛɪᴏɴ toString"
        displayNameFor(functionSubject) shouldBe "ꜰᴜɴᴄᴛɪᴏɴ hashCode"
        displayNameFor(tripleSubject) shouldBe "( \"string\", 42, 𝘤𝘭𝘢𝘴𝘴 Any )"
        displayNameFor(pairSubject) shouldBe "( \"string\", 42 )"
        displayNameFor(entrySubject) shouldBe "\"string\" → 42"
        displayNameFor(enumSubject) shouldBe "EnumSubject1"
        displayNameFor(sealedSubject) shouldBe "SealedSubject.SealedSubject1"
        displayNameFor(charSubject) shouldBe "\"c\" LATIN SMALL LETTER C"
        displayNameFor(charStringSubject) shouldBe "\"🫠\" 0x1FAE0"
        displayNameFor(blankStringSubject) shouldBe "\" \" SPACE"
        displayNameFor(emptyString) shouldBe "\"\""
        displayNameFor(stringSubject) shouldBe "\"string\""
        displayNameFor(anySubject) shouldBe "string representation"
    }

    @Test fun display_name_for__explicit() = testAll {
        displayNameFor(nullSubject, "foo {} bar") shouldBe "foo 𝘯𝘶𝘭𝘭 bar"
        displayNameFor(kPropertySubject, "foo {} bar") shouldBe "foo length bar"
        displayNameFor(kFunctionSubject, "foo {} bar") shouldBe "foo toString bar"
        displayNameFor(functionSubject, "foo {} bar") shouldBe "foo hashCode bar"
        displayNameFor(tripleSubject, "foo {}-{}-{} bar") shouldBe "foo \"string\"-42-𝘤𝘭𝘢𝘴𝘴 Any bar"
        displayNameFor(pairSubject, "foo {}-{} bar") shouldBe "foo \"string\"-42 bar"
        displayNameFor(entrySubject, "foo {}-{} bar") shouldBe "foo \"string\"-42 bar"
        displayNameFor(enumSubject, "foo {} bar") shouldBe "foo EnumSubject1 bar"
        displayNameFor(sealedSubject, "foo {} bar") shouldBe "foo SealedSubject.SealedSubject1 bar"
        displayNameFor(charSubject, "foo {} bar") shouldBe "foo \"c\" bar"
        displayNameFor(charStringSubject, "foo {} bar") shouldBe "foo \"🫠\" bar"
        displayNameFor(blankStringSubject, "foo {} bar") shouldBe "foo \" \" bar"
        displayNameFor(emptyStringSubject, "foo {} bar") shouldBe "foo \"\" bar"
        displayNameFor(stringSubject, "foo {} bar") shouldBe "foo \"string\" bar"
        displayNameFor(anySubject, "foo {} bar") shouldBe "foo string representation bar"
    }

    @TestFactory fun demo() = sequenceOf(
        kPropertySubject,
        kFunctionSubject,
        functionSubject,
        tripleSubject,
        pairSubject,
        entrySubject,
        enumSubject,
        sealedSubject,
        charSubject,
        charStringSubject,
        blankStringSubject,
        emptyStringSubject,
        stringSubject,
        nullSubject,
        anySubject,
    ).map { subject ->
        DynamicTest.dynamicTest(displayNameFor(subject)) {}
    }.asStream()
}

internal enum class EnumSubject {
    EnumSubject1,
    @Suppress("unused") ENUM_SUBJECT_2,
    ;
}

internal sealed class SealedSubject {
    @Suppress("CanSealedSubClassBeObject") class SealedSubject1 : SealedSubject()
    @Suppress("unused", "ClassName") object SEALED_SUBJECT_2 : SealedSubject()
}


internal val nullSubject: Any? = null
internal val kPropertySubject: KProperty<*> = String::length
internal val kFunctionSubject: KFunction1<*, *> = Any::toString
internal val functionSubject: Function<*> = Any::hashCode
internal val tripleSubject: Triple<*, *, *> = Triple("string", 42, Any::class)
internal val pairSubject: Pair<*, *> = Pair("string", 42)
internal val entrySubject: Entry<*, *> = mapOf(pairSubject).entries.first()
internal val enumSubject: EnumSubject = EnumSubject.EnumSubject1
internal val sealedSubject: SealedSubject = SealedSubject.SealedSubject1()
internal const val charSubject: Char = 'c'
internal const val charStringSubject: String = "🫠"
internal const val blankStringSubject: String = " "
internal const val emptyStringSubject: String = ""
internal const val stringSubject: String = "string"
internal val anySubject: Any = object {
    override fun toString(): String {
        return "string representation"
    }
}
