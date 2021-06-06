package com.zeffo.test.utils

import android.util.Log
import com.zeffo.test.features.smsreader.dto.TxnDetailsDto

object SmsReaderHelper {
    private val TAG = "SmsReader Helper"

    fun parseSms(smsBody: String, smsSender: String): TxnDetailsDto? {
        Log.d(TAG, "******** RECEIVED SMS ********")
        Log.d(TAG, "Sender : $smsSender")

        var dto: TxnDetailsDto? = null

        var isDebitMsg = Constants.CREDITS_REGEX.find(smsBody)?.value.isNullOrEmpty()
        var isPersonalMsg = smsSender.replace(Constants.ONLY_DIGITS_REGEX, "").length >= 10
        // Do not read SMS sent from a normal phone number
        if (!isPersonalMsg && isDebitMsg) {
            var amount = Constants.SMS_AMOUNT_REGEX.find(smsBody)?.value
            var merchant = Constants.SMS_MERCHANT_NAME_REGEX.find(smsBody)?.value
            var card = Constants.SMS_CARD_REGEX.find(smsBody)?.value

            var amountFormatted = amount?.replace(Constants.AMOUNT_FORMAT_REGEX, "")
            if (amountFormatted?.startsWith(".") == true) {
                amountFormatted = amountFormatted.replaceFirst(".", "")
            }
            Log.d(TAG, "Amount : $amount")
            Log.d(TAG, "Amount Formatted : $amountFormatted")
            Log.d(TAG, "Merchant : $merchant")
            Log.d(TAG, "Charged on : $card")

            val amt = amountFormatted?.toDoubleOrNull() ?: 0.0
            dto = TxnDetailsDto(amt, merchant, card)
        }
        Log.d(TAG, "******** END RECEIVED SMS ********")

        return dto
    }
}