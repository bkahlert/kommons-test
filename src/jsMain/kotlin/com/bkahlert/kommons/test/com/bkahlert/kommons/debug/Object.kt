package com.bkahlert.kommons.test.com.bkahlert.kommons.debug

/**
 * The built-in `Object` object.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Object">Object</a>
 */
internal external object Object {
    /**
     * Returns the own enumerable property names of the specified [obj] as an array,
     * iterated in the same order that a normal loop would.
     */
    fun keys(obj: Any): Array<String>
}

/**
 * Returns the own enumerable property names of this object as an array,
 * iterated in the same order that a normal loop would.
 */
public val Any.keys: Array<String> get() = Object.keys(this)
