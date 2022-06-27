package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

internal actual fun nativeObject(): Any =
    js("(function() { function Function() { this.property = \"Function-property\" }; return new Function(); })()").unsafeCast<Any>()
