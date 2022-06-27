package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.com.bkahlert.kommons.emptyString
import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.displayNameFor
import com.bkahlert.kommons.test.test
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import kotlin.collections.Map.Entry
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty


class DynamicTestDisplayNameGeneratorTest {

    @Suppress("SpellCheckingInspection")
    @TestFactory fun display_name_for2() = testEach(SealedSubject.SEALED_SUBJECT_2) {

        displayNameFor(sealedSubject) shouldBe "SealedSubject.SealedSubject1"
    }

    @Suppress("SpellCheckingInspection")
    @Test fun display_name_for() = test {
        displayNameFor(kPropertySubject) shouldBe "ᴩʀᴏᴩᴇʀᴛy length"
        displayNameFor(kFunctionSubject) shouldBe "ꜰᴜɴᴄᴛɪᴏɴ toString"
        displayNameFor(functionSubject) shouldBe "ꜰᴜɴᴄᴛɪᴏɴ hashCode"
        displayNameFor(tripleSubject) shouldBe "( \"string\", 42, class kotlin.Any )"
        displayNameFor(pairSubject) shouldBe "( \"string\", 42 )"
        displayNameFor(entrySubject) shouldBe "\"string\" → 42"
        displayNameFor(enumSubject) shouldBe "EnumSubject1"
        displayNameFor(sealedSubject) shouldBe "SealedSubject.SealedSubject1"
        displayNameFor(stringSubject) shouldBe "\"string\""
        displayNameFor(blankStringSubject) shouldBe "𝘣𝘭𝘢𝘯𝘬"
        displayNameFor(emptyString) shouldBe "𝘦𝘮𝘱𝘵𝘺"
        displayNameFor(nullSubject) shouldBe "𝘯𝘶𝘭𝘭"
        displayNameFor(anySubject) shouldBe "string representation"
    }

    @Test fun display_name_for__explicit() = test {
        displayNameFor(kPropertySubject, "foo {} bar") shouldBe "foo length bar"
        displayNameFor(kFunctionSubject, "foo {} bar") shouldBe "foo toString bar"
        displayNameFor(functionSubject, "foo {} bar") shouldBe "foo hashCode bar"
        displayNameFor(tripleSubject, "foo {}-{}-{} bar") shouldBe "foo \"string\"-42-class kotlin.Any bar"
        displayNameFor(pairSubject, "foo {}-{} bar") shouldBe "foo \"string\"-42 bar"
        displayNameFor(entrySubject, "foo {}-{} bar") shouldBe "foo \"string\"-42 bar"
        displayNameFor(enumSubject, "foo {} bar") shouldBe "foo EnumSubject1 bar"
        displayNameFor(sealedSubject, "foo {} bar") shouldBe "foo SealedSubject.SealedSubject1 bar"
        displayNameFor(stringSubject, "foo {} bar") shouldBe "foo \"string\" bar"
        displayNameFor(blankStringSubject, "foo {} bar") shouldBe "foo 𝘣𝘭𝘢𝘯𝘬 bar"
        displayNameFor(emptyStringSubject, "foo {} bar") shouldBe "foo 𝘦𝘮𝘱𝘵𝘺 bar"
        displayNameFor(nullSubject, "foo {} bar") shouldBe "foo 𝘯𝘶𝘭𝘭 bar"
        displayNameFor(anySubject, "foo {} bar") shouldBe "foo string representation bar"
    }
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


internal val kPropertySubject: KProperty<*> = String::length
internal val kFunctionSubject: KFunction1<*, *> = Any::toString
internal val functionSubject: Function<*> = Any::hashCode
internal val tripleSubject: Triple<*, *, *> = Triple("string", 42, Any::class)
internal val pairSubject: Pair<*, *> = Pair("string", 42)
internal val entrySubject: Entry<*, *> = mapOf(pairSubject).entries.first()
internal val enumSubject: EnumSubject = EnumSubject.EnumSubject1
internal val sealedSubject: SealedSubject = SealedSubject.SealedSubject1()
internal const val stringSubject: String = "string"
internal const val blankStringSubject: String = " "
internal const val emptyStringSubject: String = ""
internal val nullSubject: Any? = null
internal val anySubject: Any = object {
    override fun toString(): String {
        return "string representation"
    }
}

internal val subjects = listOf(
    nullSubject,
    kPropertySubject,
    kFunctionSubject,
    functionSubject,
    tripleSubject,
    pairSubject,
    entrySubject,
    enumSubject,
    sealedSubject,
    stringSubject,
    blankStringSubject,
    emptyStringSubject,
    anySubject,
)
