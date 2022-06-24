package com.bkahlert.kommons.test

import com.bkahlert.kommons.test.fixtures.GifImageFixture
import com.bkahlert.kommons.test.fixtures.HtmlDocumentFixture
import com.bkahlert.kommons.test.fixtures.SvgImageFixture
import com.bkahlert.kommons.test.fixtures.TextDocumentFixture
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class FixtureTest {

    @Suppress("SpellCheckingInspection")
    @Test fun gif_image_fixture() = test {
        GifImageFixture.name shouldBe "pixels.gif"
        GifImageFixture.mimeType shouldBe "image/gif"
        GifImageFixture.dataURI shouldBe """
            data:image/gif;base64,
            R0lGODdhAQADAPABAP////8AACwAAAAAAQADAAACAgxQADs=
        """.trimIndent().lineSequence().joinToString("")
    }

    @Suppress("SpellCheckingInspection")
    @Test fun html_document_fixture() = test {
        HtmlDocumentFixture.name shouldBe "hello-world.html"
        HtmlDocumentFixture.mimeType shouldBe "text/html"
        HtmlDocumentFixture.dataURI shouldBe """
            data:text/html;base64,
            PGh0bWw+CiAgPGhlYWQ+PHRpdGxlPkhlbGxvIFRpdGxlITwvdGl0bGU+CjwvaGVhZD4KPGJvZHkg
            c3R5bGU9ImJhY2tncm91bmQ6IHVybCgnZGF0YTppbWFnZS9naWY7YmFzZTY0LFIwbEdPRGRoQVFB
            REFQQUJBUC8vLy84QUFDd0FBQUFBQVFBREFBQUNBZ3hRQURzPScpIj4KICAgIDxoMT5IZWxsbyBI
            ZWFkbGluZSE8L2gxPgogICAgPHA+SGVsbG8gV29ybGQhPC9wPgo8L2JvZHk+CjwvaHRtbD4=
        """.trimIndent().lineSequence().joinToString("")
    }

    @Suppress("SpellCheckingInspection")
    @Test fun svg_image_fixture() = test {
        SvgImageFixture.name shouldBe "kommons.svg"
        SvgImageFixture.mimeType shouldBe "image/svg+xml"
        SvgImageFixture.dataURI shouldBe """
            data:image/svg+xml;base64,
            PHN2ZyB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgYXJp
            YS1sYWJlbD0iS29tbW9ucyIgcm9sZT0iaW1nIiB2aWV3Qm94PSIwIDAgNjAgNjAiIHN0eWxlPSJj
            dXJzb3I6IGRlZmF1bHQ7Ij4KICAgIDxkZWZzPgogICAgICAgIDxsaW5lYXJHcmFkaWVudCBpZD0i
            dXBwZXItayIgeDE9Ii03MjAiIHkxPSI3ODAiIHgyPSIwIiB5Mj0iNjAiPgogICAgICAgICAgICA8
            c3RvcCBvZmZzZXQ9IjAiIHN0b3AtY29sb3I9IiMyOWFiZTIiLz4KICAgICAgICAgICAgPHN0b3Ag
            b2Zmc2V0PSIuMSIgc3RvcC1jb2xvcj0iI2EzNWViYiIvPgogICAgICAgICAgICA8c3RvcCBvZmZz
            ZXQ9Ii4yIiBzdG9wLWNvbG9yPSIjMDA5MGFhIi8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0i
            LjMiIHN0b3AtY29sb3I9IiMwMTgxOGYiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIuMyIg
            c3RvcC1jb2xvcj0iIzAxODE4ZiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii40NSIgc3Rv
            cC1jb2xvcj0iIzAwOTc4YiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii42IiBzdG9wLWNv
            bG9yPSIjNjQ4YmUwIi8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iLjciIHN0b3AtY29sb3I9
            IiMwMDllYzYiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIuOCIgc3RvcC1jb2xvcj0iIzI5
            YWJlMiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii45IiBzdG9wLWNvbG9yPSIjMDRhOTcx
            Ii8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iMSIgc3RvcC1jb2xvcj0iIzA0YTk3MSIvPgog
            ICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJ4MSIgZHVyPSI2MHMiIHZhbHVlcz0i
            MDsgLTcyMDsgMCIgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiLz4KICAgICAgICAgICAgPGFuaW1h
            dGUgYXR0cmlidXRlTmFtZT0ieTEiIGR1cj0iNjBzIiB2YWx1ZXM9IjYwOyA3ODA7IDYwIiByZXBl
            YXRDb3VudD0iaW5kZWZpbml0ZSIvPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1l
            PSJ4MiIgZHVyPSI2MHMiIHZhbHVlcz0iNzIwOyAwOyA3MjAiIHJlcGVhdENvdW50PSJpbmRlZmlu
            aXRlIi8+CiAgICAgICAgICAgIDxhbmltYXRlIGF0dHJpYnV0ZU5hbWU9InkyIiBkdXI9IjYwcyIg
            dmFsdWVzPSItNjYwOyA2MDsgLTY2MCIgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiLz4KICAgICAg
            ICA8L2xpbmVhckdyYWRpZW50PgogICAgICAgIDxsaW5lYXJHcmFkaWVudCBpZD0ic3RyaXAiIHgx
            PSIwIiB5MT0iNjAiIHgyPSIzMDAiIHkyPSItMjQwIj4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0
            PSIwIiBzdG9wLWNvbG9yPSIjQzc1N0JDIi8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iLjAy
            NSIgc3RvcC1jb2xvcj0iI0QwNjA5QSIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii4wNSIg
            c3RvcC1jb2xvcj0iI0UxNzI1QyIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii4wNzUiIHN0
            b3AtY29sb3I9IiNFRTdFMkYiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIuMSIgc3RvcC1j
            b2xvcj0iI0Y1ODYxMyIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii4yIiBzdG9wLWNvbG9y
            PSIjRjg4OTA5Ii8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iLjUiIHN0b3AtY29sb3I9IiNm
            ZjkyMzQiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIxIiBzdG9wLWNvbG9yPSIjZWJiMjFk
            Ii8+CiAgICAgICAgICAgIDxhbmltYXRlIGF0dHJpYnV0ZU5hbWU9IngxIiBkdXI9IjIwcyIgdmFs
            dWVzPSIwOyAtMzAwOyAwIiByZXBlYXRDb3VudD0iaW5kZWZpbml0ZSIvPgogICAgICAgICAgICA8
            YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJ5MSIgZHVyPSIyMHMiIHZhbHVlcz0iNjA7IDM2MDsgNjAi
            IHJlcGVhdENvdW50PSJpbmRlZmluaXRlIi8+CiAgICAgICAgICAgIDxhbmltYXRlIGF0dHJpYnV0
            ZU5hbWU9IngyIiBkdXI9IjIwcyIgdmFsdWVzPSIzMDA7IDA7IDMwMCIgcmVwZWF0Q291bnQ9Imlu
            ZGVmaW5pdGUiLz4KICAgICAgICAgICAgPGFuaW1hdGUgYXR0cmlidXRlTmFtZT0ieTIiIGR1cj0i
            MjBzIiB2YWx1ZXM9Ii0yNDA7IDYwOyAtMjQwIiByZXBlYXRDb3VudD0iaW5kZWZpbml0ZSIvPgog
            ICAgICAgIDwvbGluZWFyR3JhZGllbnQ+CiAgICAgICAgPGxpbmVhckdyYWRpZW50IGlkPSJsb3dl
            ci1rIiB4MT0iMCIgeTE9IjYwIiB4Mj0iNjAwIiB5Mj0iLTU0MCI+CiAgICAgICAgICAgIDxzdG9w
            IG9mZnNldD0iMCIgc3RvcC1jb2xvcj0iIzI5YWJlMiIvPgogICAgICAgICAgICA8c3RvcCBvZmZz
            ZXQ9Ii4xIiBzdG9wLWNvbG9yPSIjYTM1ZWJiIi8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0i
            LjIiIHN0b3AtY29sb3I9IiMwMDkwYWEiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIuMyIg
            c3RvcC1jb2xvcj0iIzAxODE4ZiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii40NSIgc3Rv
            cC1jb2xvcj0iIzAwOTc4YiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii42IiBzdG9wLWNv
            bG9yPSIjNjQ4YmUwIi8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iLjciIHN0b3AtY29sb3I9
            IiMwMDllYzYiLz4KICAgICAgICAgICAgPHN0b3Agb2Zmc2V0PSIuOCIgc3RvcC1jb2xvcj0iIzI5
            YWJlMiIvPgogICAgICAgICAgICA8c3RvcCBvZmZzZXQ9Ii45IiBzdG9wLWNvbG9yPSIjMDRhOTcx
            Ii8+CiAgICAgICAgICAgIDxzdG9wIG9mZnNldD0iMSIgc3RvcC1jb2xvcj0iIzA0YTk3MSIvPgog
            ICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1lPSJ4MSIgZHVyPSI4MHMiIHZhbHVlcz0i
            MDsgLTYwMDsgMCIgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiLz4KICAgICAgICAgICAgPGFuaW1h
            dGUgYXR0cmlidXRlTmFtZT0ieTEiIGR1cj0iODBzIiB2YWx1ZXM9IjYwOyA2NjA7IDYwIiByZXBl
            YXRDb3VudD0iaW5kZWZpbml0ZSIvPgogICAgICAgICAgICA8YW5pbWF0ZSBhdHRyaWJ1dGVOYW1l
            PSJ4MiIgZHVyPSI4MHMiIHZhbHVlcz0iNjAwOyAwOyA2MDAiIHJlcGVhdENvdW50PSJpbmRlZmlu
            aXRlIi8+CiAgICAgICAgICAgIDxhbmltYXRlIGF0dHJpYnV0ZU5hbWU9InkyIiBkdXI9IjgwcyIg
            dmFsdWVzPSItNTQwOyA2MDsgLTU0MCIgcmVwZWF0Q291bnQ9ImluZGVmaW5pdGUiLz4KICAgICAg
            ICA8L2xpbmVhckdyYWRpZW50PgogICAgPC9kZWZzPgogICAgPHBvbHlnb24gcG9pbnRzPSIwLDAg
            MzAuNSwwIDAsMzAuNSIgZmlsbD0idXJsKCN1cHBlci1rKSIvPgogICAgPHBvbHlnb24gcG9pbnRz
            PSIzMC41LDAgMCwzMC41IDAsNjAgNjAsMCIgZmlsbD0idXJsKCNzdHJpcCkiLz4KICAgIDxwb2x5
            Z29uIHBvaW50cz0iMCw2MCAzMCwzMCA2MCw2MCIgZmlsbD0idXJsKCNsb3dlci1rKSIvPgo8L3N2
            Zz4=
        """.trimIndent().lineSequence().joinToString("")
    }

    @Suppress("SpellCheckingInspection")
    @Test fun text_document_fixture() = test {
        TextDocumentFixture.name shouldBe "unicode.txt"
        TextDocumentFixture.mimeType shouldBe "text/plain"
        TextDocumentFixture.dataURI shouldBe """
            data:text/plain;base64,
            YcKF8J2Vkw0K4piwCvCfkYsK
        """.trimIndent().lineSequence().joinToString("")
    }
}
