package com.bkahlert.kommons.test.junit

import com.bkahlert.kommons.test.junit.SimpleId.Companion.simpleId
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.support.TypeBasedParameterResolver

public class SimpleIdResolver : TypeBasedParameterResolver<SimpleId>() {
    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): SimpleId =
        extensionContext.simpleId
}
