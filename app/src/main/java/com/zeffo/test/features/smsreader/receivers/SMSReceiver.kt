package com.zeffo.test.features.smsreader.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import androidx.core.content.ContextCompat
import com.zeffo.test.features.smsreader.dto.TxnDetailsDto
import com.zeffo.test.features.smsreader.services.TransactionSyncService
import com.zeffo.test.utils.Constants
import com.zeffo.test.utils.SmsReaderHelper


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

            val dto = SmsReaderHelper.parseSms(smsBody, smsSender)

            context?.let { cntxt ->
                val serviceIntent = Intent(cntxt, TransactionSyncService::class.java)
                serviceIntent.putExtra("amount", dto?.amount ?: 0.0)
                serviceIntent.putExtra("merchant", dto?.merchant)
                serviceIntent.putExtra("card", dto?.card)
                ContextCompat.startForegroundService(cntxt, serviceIntent)
            }
        }
    }

}