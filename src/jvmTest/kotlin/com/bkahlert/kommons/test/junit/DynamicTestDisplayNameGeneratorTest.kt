package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.DynamicTestDisplayNameGenerator.displayNameFor
import com.bkahlert.kommons.test.tests
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.reflect.KFunction1
import kotlin.reflect.KProperty


class DynamicTestDisplayNameGeneratorTest {

    @Test fun display_name_for() = tests {
        val kPropertySubject: KProperty<*> = String::length
        val kFunctionSubject: KFunction1<*, *> = Any::toString
        val functionSubject: Function<*> = Any::hashCode
        val tripleSubject: Triple<*, *, *> = Triple("string", 42, Any::class)
        val pairSubject: Pair<*, *> = Pair("string", 42)
        val anySubject: Any = "string"

        displayNameFor(null) shouldBe "❮ ␀ ❯"
        displayNameFor(kPropertySubject) shouldBe "property ❮ length ❯"
        displayNameFor(kFunctionSubject) shouldBe "function ❮ toString ❯"
        displayNameFor(functionSubject) shouldBe "function ❮ hashCode ❯"
        displayNameFor(tripleSubject) shouldBe "❪ \"string\", 42, class kotlin.Any ❫"
        displayNameFor(pairSubject) shouldBe "❪ \"string\", 42 ❫"
        displayNameFor(anySubject) shouldBe "❮ \"string\" ❯"

        displayNameFor(null, "foo {} bar") shouldBe "foo ␀ bar"
        displayNameFor(kPropertySubject, "foo {} bar") shouldBe "foo length bar"
        displayNameFor(kFunctionSubject, "foo {} bar") shouldBe "foo toString bar"
        displayNameFor(functionSubject, "foo {} bar") shouldBe "foo hashCode bar"
        displayNameFor(tripleSubject, "foo {}-{}-{} bar") shouldBe "foo \"string\"-42-class kotlin.Any bar"
        displayNameFor(pairSubject, "foo {}-{} bar") shouldBe "foo \"string\"-42 bar"
        displayNameFor(anySubject, "foo {} bar") shouldBe "foo \"string\" bar"
    }
}
