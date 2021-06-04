package com.zeffo.test.utils

object Constants {
    val SMS_AMOUNT_REGEX = Regex("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)")
    val SMS_MERCHANT_NAME_REGEX = Regex("(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)")
    val SMS_CARD_REGEX = Regex("(?i)(?:\\smade on|ur|made a\\s|in\\*)([A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?)")
    val AMOUNT_FORMAT_REGEX = Regex("(\\d+\\.?\\d{1,})")
    val ONLY_DIGITS_REGEX = Regex("[^\\d]")
}