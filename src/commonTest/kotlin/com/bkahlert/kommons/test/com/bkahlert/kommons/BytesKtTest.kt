package com.bkahlert.kommons.test.com.bkahlert.kommons

import com.bkahlert.kommons.test.testAll
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class BytesTest {


    @Test fun encode_to_base64() = base64Bytes.testAll { (bytes, base64) ->
        bytes.encodeToBase64() shouldBe base64
    }

    @Test fun encode_to_base64_url_safe() {
        byteArrayOf(248.toByte()).encodeToBase64(urlSafe = true) shouldBe "-A%3d%3d\r\n"
        byteArrayOf(252.toByte()).encodeToBase64(urlSafe = true) shouldBe "_A%3d%3d\r\n"
    }

    @Test fun encode_to_base64_no_chunking() {
        byteArrayOf(248.toByte()).encodeToBase64(chunked = false) shouldBe "+A=="
        byteArrayOf(252.toByte()).encodeToBase64(chunked = false) shouldBe "/A=="
    }

    @Test fun decode_from_base64() = base64Bytes.testAll { (bytes, base64) ->
        base64.decodeFromBase64() shouldBe bytes
    }

    @Test fun decode_from_base64_url_safe() {
        "-A%3d%3d\r\n".decodeFromBase64() shouldBe byteArrayOf(248.toByte())
        "_A%3d%3d\r\n".decodeFromBase64() shouldBe byteArrayOf(252.toByte())
    }

    @Test fun decode_from_base64_no_chunking() {
        "+A==".decodeFromBase64() shouldBe byteArrayOf(248.toByte())
        "/A==".decodeFromBase64() shouldBe byteArrayOf(252.toByte())
    }
}

@Suppress("SpellCheckingInspection")
internal val base64Bytes = listOf(
    byteArrayOf() to "",
    byteArrayOf(0) to "AA==\r\n",
    byteArrayOf(0, 0) to "AAA=\r\n",
    byteArrayOf(0, 0, 0) to "AAAA\r\n",
    byteArrayOf(0, 0, 0, 0) to "AAAAAA==\r\n",
    byteArrayOf(-1) to "/w==\r\n",
    byteArrayOf(-1, -1) to "//8=\r\n",
    byteArrayOf(-1, -1, -1) to "////\r\n",
    byteArrayOf(-1, -1, -1, -1) to "/////w==\r\n",
)
