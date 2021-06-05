package com.zeffo.test.utils

object Constants {
    val SMS_AMOUNT_REGEX = Regex("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)")
    val SMS_MERCHANT_NAME_REGEX = Regex("(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)")
    val SMS_CARD_REGEX = Regex("(?i)(?:\\smade on|ur|made a\\s|in\\*)([A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?)")
    val AMOUNT_FORMAT_REGEX = Regex("[^(\\d+\\.?\\d)]")
    val ONLY_DIGITS_REGEX = Regex("[^\\d]")

    const val NOTIFICATION_CHANNEL_ID = "ZeffoSmsServiceChannel"
    const val NOTIFICATION_CHANNEL_NAME = "Zeffo SMS Service Channel"
    const val START_FOREGROUND_ACTION = "com.zeffo.test.features.smsreader.services.TransactionSyncService.action.startforeground"
    const val STOP_FOREGROUND_ACTION = "com.zeffo.test.features.smsreader.services.TransactionSyncService.stopforeground"
    const val FOREGROUND_SERVICE_NOTIFICATION_ID = 101
}