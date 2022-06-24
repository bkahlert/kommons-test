package com.bkahlert.kommons.test.com.bkahlert.kommons


/**
 * Named Unicode code points, like [Unicode.LINE_FEED] or [Unicode.SYMBOL_FOR_START_OF_HEADING].
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
internal object Unicode {

    /** [NULL](https://codepoints.net/U+0000) */
    const val NULL: Char = '\u0000'

    /** [START OF HEADING](https://codepoints.net/U+0001) */
    const val START_OF_HEADING: Char = '\u0001'

    /** [START OF TEXT](https://codepoints.net/U+0002) */
    const val START_OF_TEXT: Char = '\u0002'

    /** [END OF TEXT](https://codepoints.net/U+0003) */
    const val END_OF_TEXT: Char = '\u0003'

    /** [END OF TRANSMISSION](https://codepoints.net/U+0004) */
    const val END_OF_TRANSMISSION: Char = '\u0004'

    /** [ENQUIRY](https://codepoints.net/U+0005) */
    const val ENQUIRY: Char = '\u0005'

    /** [ACKNOWLEDGE](https://codepoints.net/U+0006) */
    const val ACKNOWLEDGE: Char = '\u0006'

    /** [BELL](https://codepoints.net/U+0007) */
    const val BELL: Char = '\u0007'

    /** [BACKSPACE](https://codepoints.net/U+0008) */
    const val BACKSPACE: Char = '\u0008'

    /** [CHARACTER TABULATION](https://codepoints.net/U+0009) */
    const val CHARACTER_TABULATION: Char = '\u0009'

    /** Synonym for [CHARACTER_TABULATION] */
    const val TAB: Char = CHARACTER_TABULATION

    /** [LINE FEED (LF)](https://codepoints.net/U+000A) */
    const val LINE_FEED: Char = '\u000A'

    /** [LINE TABULATION](https://codepoints.net/U+000B) */
    const val LINE_TABULATION: Char = '\u000B'

    /** [FORM FEED (FF)](https://codepoints.net/U+000C) */
    const val FORM_FEED: Char = '\u000C'

    /** [CARRIAGE RETURN (CR)](https://codepoints.net/U+000D) */
    const val CARRIAGE_RETURN: Char = '\u000D'

    /** [SHIFT OUT](https://codepoints.net/U+000E) */
    const val SHIFT_OUT: Char = '\u000E'

    /** [SHIFT IN](https://codepoints.net/U+000F) */
    const val SHIFT_IN: Char = '\u000F'

    /** [DATA LINK ESCAPE](https://codepoints.net/U+0010) */
    const val DATA_LINK_ESCAPE: Char = '\u0010'

    /** [DEVICE CONTROL ONE](https://codepoints.net/U+0011) */
    const val DEVICE_CONTROL_ONE: Char = '\u0011'

    /** [DEVICE CONTROL TWO](https://codepoints.net/U+0012) */
    const val DEVICE_CONTROL_TWO: Char = '\u0012'

    /** [DEVICE CONTROL THREE](https://codepoints.net/U+0013) */
    const val DEVICE_CONTROL_THREE: Char = '\u0013'

    /** [DEVICE CONTROL FOUR](https://codepoints.net/U+0014) */
    const val DEVICE_CONTROL_FOUR: Char = '\u0014'

    /** [NEGATIVE ACKNOWLEDGE](https://codepoints.net/U+0015) */
    const val NEGATIVE_ACKNOWLEDGE: Char = '\u0015'

    /** [SYNCHRONOUS IDLE](https://codepoints.net/U+0016) */
    const val SYNCHRONOUS_IDLE: Char = '\u0016'

    /** [END OF TRANSMISSION BLOCK](https://codepoints.net/U+0017) */
    const val END_OF_TRANSMISSION_BLOCK: Char = '\u0017'

    /** [CANCEL](https://codepoints.net/U+0018) */
    const val CANCEL: Char = '\u0018'

    /** [END OF MEDIUM](https://codepoints.net/U+0019) */
    const val END_OF_MEDIUM: Char = '\u0019'

    /** [SUBSTITUTE](https://codepoints.net/U+001A) */
    const val SUBSTITUTE: Char = '\u001A'

    /** [ESCAPE](https://codepoints.net/U+001B) */
    const val ESCAPE: Char = '\u001B'

    /** Synonym for [ESCAPE] */
    const val ESC: Char = ESCAPE

    /** [INFORMATION SEPARATOR FOUR](https://codepoints.net/U+001C) */
    const val INFORMATION_SEPARATOR_FOUR: Char = '\u001C'

    /** [INFORMATION SEPARATOR THREE](https://codepoints.net/U+001D) */
    const val INFORMATION_SEPARATOR_THREE: Char = '\u001D'

    /** [INFORMATION_SEPARATOR_TWO](https://codepoints.net/U+001E) */
    const val INFORMATION_SEPARATOR_TWO: Char = '\u001E'

    /** [INFORMATION_SEPARATOR_ONE](https://codepoints.net/U+001F) */
    const val INFORMATION_SEPARATOR_ONE: Char = '\u001F'

    /** [DELETE](https://codepoints.net/U+007F) */
    const val DELETE: Char = '\u007F'

    /** [CONTROL SEQUENCE INTRODUCER](https://codepoints.net/U+009B) */
    const val CONTROL_SEQUENCE_INTRODUCER: Char = '\u009B'

    /** Synonym for [CONTROL_SEQUENCE_INTRODUCER] */
    const val CSI: Char = CONTROL_SEQUENCE_INTRODUCER

    /** [NO-BREAK SPACE](https://codepoints.net/U+00A0) */
    const val NO_BREAK_SPACE: Char = '\u00A0'

    /** Synonym for [NO_BREAK_SPACE] */
    const val NBSP: Char = NO_BREAK_SPACE

    /** [FIGURE SPACE](https://codepoints.net/U+2007) */
    const val FIGURE_SPACE: Char = '\u2007'

    /** [ZERO WIDTH SPACE](https://codepoints.net/U+200B) */
    const val ZERO_WIDTH_SPACE: Char = '\u200B'

    /** [ZERO WIDTH NON-JOINER](https://codepoints.net/U+200C) */
    const val ZERO_WIDTH_NON_JOINER: Char = '\u200C'

    /** [ZERO WIDTH JOINER](https://codepoints.net/U+200D) */
    const val ZERO_WIDTH_JOINER: Char = '\u200D'

    /** Synonym for [ZERO_WIDTH_JOINER] */
    const val ZWJ: Char = ZERO_WIDTH_JOINER

    /** [HORIZONTAL ELLIPSIS](https://codepoints.net/U+2026) */
    const val HORIZONTAL_ELLIPSIS: Char = '\u2026'

    /** Synonym for [HORIZONTAL_ELLIPSIS] */
    const val ELLIPSIS: Char = HORIZONTAL_ELLIPSIS


    /** [LINE SEPARATOR](https://codepoints.net/U+2028) */
    const val LINE_SEPARATOR: Char = '\u2028'

    /** [PARAGRAPH SEPARATOR](https://codepoints.net/U+2029) */
    const val PARAGRAPH_SEPARATOR: Char = '\u2029'

    /** [NARROW NO-BREAK SPACE](https://codepoints.net/U+202F) */
    const val NARROW_NO_BREAK_SPACE: Char = '\u202F'

    /** [NEXT LINE (NEL)](https://codepoints.net/U+0085) */
    const val NEXT_LINE: Char = '\u0085'

    /** [PILCROW SIGN](https://codepoints.net/U+00B6) ¶ */
    const val PILCROW_SIGN: Char = '\u00B6'

    /** [RIGHT-TO-LEFT MARK](https://codepoints.net/U+200F) */
    const val RIGHT_TO_LEFT_MARK: Char = '\u200F'

    /** [SYMBOL FOR NULL](https://codepoints.net/U+2400) `␀` */
    const val SYMBOL_FOR_NULL: Char = '\u2400'

    /** [SYMBOL FOR START OF HEADING](https://codepoints.net/U+2401) `␁` */
    const val SYMBOL_FOR_START_OF_HEADING: Char = '\u2401'

    /** [SYMBOL FOR START OF TEXT](https://codepoints.net/U+2402) `␂` */
    const val SYMBOL_FOR_START_OF_TEXT: Char = '\u2402'

    /** [SYMBOL FOR END OF TEXT](https://codepoints.net/U+2403) `␃` */
    const val SYMBOL_FOR_END_OF_TEXT: Char = '\u2403'

    /** [SYMBOL FOR END OF TRANSMISSION](https://codepoints.net/U+2404) `␄` */
    const val SYMBOL_FOR_END_OF_TRANSMISSION: Char = '\u2404'

    /** [SYMBOL FOR ENQUIRY](https://codepoints.net/U+2405) `␅` */
    const val SYMBOL_FOR_ENQUIRY: Char = '\u2405'

    /** [SYMBOL FOR ACKNOWLEDGE](https://codepoints.net/U+2406) `␆` */
    const val SYMBOL_FOR_ACKNOWLEDGE: Char = '\u2406'

    /** [SYMBOL FOR BELL](https://codepoints.net/U+2407) `␇` */
    const val SYMBOL_FOR_BELL: Char = '\u2407'

    /** [SYMBOL FOR BACKSPACE](https://codepoints.net/U+2408) `␈` */
    const val SYMBOL_FOR_BACKSPACE: Char = '\u2408'

    /** [SYMBOL FOR HORIZONTAL TABULATION](https://codepoints.net/U+2409) `␉` */
    const val SYMBOL_FOR_HORIZONTAL_TABULATION: Char = '\u2409'

    /** [SYMBOL FOR LINE FEED](https://codepoints.net/U+240A) `␊` */
    const val SYMBOL_FOR_LINE_FEED: Char = '\u240A'

    /** [SYMBOL FOR VERTICAL TABULATION](https://codepoints.net/U+240B) `␋` */
    const val SYMBOL_FOR_VERTICAL_TABULATION: Char = '\u240B'

    /** [SYMBOL FOR FORM FEED](https://codepoints.net/U+240C) `␌` */
    const val SYMBOL_FOR_FORM_FEED: Char = '\u240C'

    /** [SYMBOL FOR CARRIAGE RETURN](https://codepoints.net/U+240D) `␍` */
    const val SYMBOL_FOR_CARRIAGE_RETURN: Char = '\u240D'

    /** [SYMBOL FOR SHIFT OUT](https://codepoints.net/U+240E) `␎` */
    const val SYMBOL_FOR_SHIFT_OUT: Char = '\u240E'

    /** [SYMBOL FOR SHIFT IN](https://codepoints.net/U+240F) `␏` */
    const val SYMBOL_FOR_SHIFT_IN: Char = '\u240F'

    /** [SYMBOL FOR DATA LINK ESCAPE](https://codepoints.net/U+2410) `␐` */
    const val SYMBOL_FOR_DATA_LINK_ESCAPE: Char = '\u2410'

    /** [SYMBOL FOR DEVICE CONTROL ONE](https://codepoints.net/U+2411) `␑` */
    const val SYMBOL_FOR_DEVICE_CONTROL_ONE: Char = '\u2411'

    /** [SYMBOL FOR DEVICE CONTROL TWO](https://codepoints.net/U+2412) `␒` */
    const val SYMBOL_FOR_DEVICE_CONTROL_TWO: Char = '\u2412'

    /** [SYMBOL FOR DEVICE CONTROL THREE](https://codepoints.net/U+2413) `␓` */
    const val SYMBOL_FOR_DEVICE_CONTROL_THREE: Char = '\u2413'

    /** [SYMBOL FOR DEVICE CONTROL FOUR](https://codepoints.net/U+2414) `␔` */
    const val SYMBOL_FOR_DEVICE_CONTROL_FOUR: Char = '\u2414'

    /** [SYMBOL FOR NEGATIVE ACKNOWLEDGE](https://codepoints.net/U+2415) `␕` */
    const val SYMBOL_FOR_NEGATIVE_ACKNOWLEDGE: Char = '\u2415'

    /** [SYMBOL FOR SYNCHRONOUS IDLE](https://codepoints.net/U+2416) `␖` */
    const val SYMBOL_FOR_SYNCHRONOUS_IDLE: Char = '\u2416'

    /** [SYMBOL FOR END OF TRANSMISSION BLOCK](https://codepoints.net/U+2417) `␗` */
    const val SYMBOL_FOR_END_OF_TRANSMISSION_BLOCK: Char = '\u2417'

    /** [SYMBOL FOR CANCEL](https://codepoints.net/U+2418) `␘` */
    const val SYMBOL_FOR_CANCEL: Char = '\u2418'

    /** [SYMBOL FOR END OF MEDIUM](https://codepoints.net/U+2419) `␙` */
    const val SYMBOL_FOR_END_OF_MEDIUM: Char = '\u2419'

    /** [SYMBOL FOR SUBSTITUTE](https://codepoints.net/U+241A) `␚` */
    const val SYMBOL_FOR_SUBSTITUTE: Char = '\u241A'

    /** [SYMBOL FOR ESCAPE](https://codepoints.net/U+241B) `␛` */
    const val SYMBOL_FOR_ESCAPE: Char = '\u241B'

    /** [SYMBOL FOR FILE SEPARATOR](https://codepoints.net/U+241C) `␜` */
    const val SYMBOL_FOR_FILE_SEPARATOR: Char = '\u241C'

    /** [SYMBOL FOR GROUP SEPARATOR](https://codepoints.net/U+241D) `␝` */
    const val SYMBOL_FOR_GROUP_SEPARATOR: Char = '\u241D'

    /** [SYMBOL FOR RECORD SEPARATOR](https://codepoints.net/U+241E) `␞` */
    const val SYMBOL_FOR_RECORD_SEPARATOR: Char = '\u241E'

    /** [SYMBOL FOR UNIT SEPARATOR](https://codepoints.net/U+241F) `␟` */
    const val SYMBOL_FOR_UNIT_SEPARATOR: Char = '\u241F'

    /** [SYMBOL FOR DELETE](https://codepoints.net/U+2421) `␡` */
    const val SYMBOL_FOR_DELETE: Char = '\u2421'
}
