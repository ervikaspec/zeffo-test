package com.zeffo.test.features.smsreader.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import androidx.core.content.ContextCompat
import com.zeffo.test.features.smsreader.services.TransactionSyncService
import com.zeffo.test.utils.Constants


class SMSReceiver : BroadcastReceiver() {

    private val TAG = "SMSReader Receiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            var smsSender = ""
            var smsBody = ""
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsSender = smsMessage.displayOriginatingAddress
                smsBody += smsMessage.messageBody
            }

            Log.d(TAG, "******** RECEIVED SMS ********")
            Log.d(TAG, "Sender : $smsSender")

            // Do not read SMS sent from a normal phone number
            if (smsSender.replace(Constants.ONLY_DIGITS_REGEX, "").length >1) {
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

                context?.let { cntxt ->
                    val serviceIntent = Intent(cntxt, TransactionSyncService::class.java)
                    serviceIntent.putExtra("amount", amountFormatted)
                    ContextCompat.startForegroundService(cntxt, serviceIntent)
                }
            }
            Log.d(TAG, "******** END RECEIVED SMS ********")
        }
    }

}