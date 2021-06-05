package com.zeffo.test.features.smsreader.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import com.zeffo.test.R
import com.zeffo.test.features.smsreader.dto.SaveTxnRequest
import com.zeffo.test.features.smsreader.dto.TxnDetailsDto
import com.zeffo.test.features.smsreader.dto.TxnResponseDto
import com.zeffo.test.features.smsreader.repository.TxnRepository
import com.zeffo.test.features.smsreader.view.MainActivity
import com.zeffo.test.network.ResponseCallback
import com.zeffo.test.network.ZeffoResultState
import com.zeffo.test.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class TransactionSyncService : Service(), CoroutineScope {

    val TAG = "SMSReaderService"
    private val serviceJob = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + serviceJob

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val amount = intent.getDoubleExtra("amount", 0.0)
        val merchant = intent.getStringExtra("merchant") ?: ""
        val card = intent.getStringExtra("card") ?: ""

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification: Notification = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Zeffo")
            .setContentText("Transaction Sync Service")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNotificationManager.createNotificationChannel(channel)
            NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
        }
        startForeground(Constants.FOREGROUND_SERVICE_NOTIFICATION_ID, notification)

        val txn = TxnDetailsDto(amount, merchant, card)
        val dao = SaveTxnRequest(device_id = 123, user_id = "111", txn)
        syncData(dao)
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun syncData(dao: SaveTxnRequest) = launch(serviceJob) {
        try {
            TxnRepository.callTxnAPI(dao, object : ResponseCallback<ZeffoResultState<TxnResponseDto>> {
                override fun onResponse(resp: ZeffoResultState<TxnResponseDto>) {
                    when (resp) {
                        is ZeffoResultState.Loading -> {
                            Log.d(TAG, "***** Loading *****")
                        }
                        is ZeffoResultState.Success -> {
                            Log.d(TAG, "***** Saved Txn Details *****")
                        }
                        is ZeffoResultState.Error -> {
                            Log.d(TAG, "***** Error saving Txn Details *****")
                            Log.d(TAG, resp.error.errorMessage ?: "")
                        }
                    }
                }
            })
        } catch(e: Exception) {
            // handle exception
        } finally {
            stopSelf()
        }
    }

}