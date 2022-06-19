package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.TesterTest.PlainAssertionsTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInfo
import org.junit.platform.engine.UniqueId.parse

class UniqueIdTest {

    @Test
    fun `should resolve unique id`(uniqueId: UniqueId) {
        uniqueId.toString() shouldBe "UniqueIdTest.should_resolve_unique_id"
    }

    @Test
    fun `should resolve unique id`(uniqueId: UniqueId, @Suppress("UNUSED_PARAMETER") `with differing arguments`: TestInfo) {
        uniqueId.toString() shouldBe "UniqueIdTest.should_resolve_unique_id-TestInfo"
    }

    @TestFactory
    fun `should resolve dynamic unique id`(uniqueId: UniqueId): DynamicContainer =
        dynamicContainer("dynamic container", listOf(
            dynamicTest("dynamic test/container") {
                uniqueId.toString() shouldBe "UniqueIdTest.should_resolve_dynamic_unique_id"
            },
            dynamicTest("dynamic test") {
                uniqueId.toString() shouldBe "UniqueIdTest.should_resolve_dynamic_unique_id"
            }
        ))

    @TestFactory
    fun `should instantiate unique id`() = testEach(
        UniqueId.from(parse("[engine:junit-jupiter]/[class:com.bkahlert.kommons.test.TesterTest]/[nested-class:PlainAssertionsTest]/[method:method name()]")),
        UniqueId.from(StackTraceElement(PlainAssertionsTest::class.java.name, "method name", "Tests.kt", 1135)),
    ) {
        expecting { value } that { it shouldBe "TesterTest.PlainAssertionsTest.method_name" }
    }
}
