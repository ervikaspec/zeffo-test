package com.zeffo.test.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SmsReaderHelperTest {

    data class SMSData(
        var smsBody: String,
        var smsSender: String,
        var amount: Double
    )

    val smsMap = HashMap<String, SMSData>()

    private fun getTestData() {
        smsMap["1"] = SMSData(
            "ALERT: You've spent Rs.700.00  on CREDIT Card xx1234 at SHARMA MEDICAL STORE on 2021-05-14:14:03:37.Avl bal - Rs.7804.00, curr o/s - Rs.2446.00.Not you? Call 19002986321.",
            "AM-ICICI",
            700.0)

        smsMap["2"] = SMSData(
            "UPDATE: INR 10,177.00 debited from A/c XX6791 on 30-MAY-21. Info: UPI-MOBIKWIK-mobikwikp2m@hdfcbank-NA. Avl bal:INR 1,898.67",
            "BK-HDFC",
            10177.0)

        smsMap["3"] = SMSData(
            "Rs. 800.00 credited to a/c XXXXXX3891 on 04-06-21 by a/c linked to VPA abcd@okhdfcbank (UPI Ref No  11550).",
            "VM-HDFCBK",
            0.0)

        smsMap["4"] = SMSData(
            "Showroom Open. Rate 47900/-. 0% making on All Hallmark Gold Jewellery n Buy1-Get1 Free Certified Diamond Jewellery at PP Jewellers, SCF24-22D, Chd. 07740006060",
            "VM-HDFCBK",
            0.0)

        smsMap["5"] = SMSData(
            "UPDATE:Available balance in A/c XX7981 on 30-MAY-21:INR 1,968.67.Credits in A/c subject to clearing.To register for WhatsApp Banking,click ...",
            "VM-HDFCBK",
            0.0)

        smsMap["6"] = SMSData(
            "HDFC Bank Cardmember, Payment of Rs 8966 was credited to your card ending 9796 on 28/MAY/2021.",
            "CA-HDFCBK",
            0.0)

        smsMap["7"] = SMSData(
            "UPDATE: Your Mutual Fund bill payment of Rs. 1000.00 for MA has been processed successfully.",
            "VM-HDFCBK",
            1000.0)

        smsMap["8"] = SMSData(
            "Thanks for paying Rs.1,500.00 from A/c XXXX3091 to PAYUCREDCLUB via NetBanking. Call 18002098061 if txn not done by you",
            "VM-HDFCBK",
            1500.0)
    }

    @Test
    fun testSmsParser() {
        getTestData()

        for (smsData in smsMap.values) {
            val dto = SmsReaderHelper.parseSms(smsData.smsBody, smsData.smsSender)
            assertThat(dto?.amount ?: 0.0).isEqualTo(smsData.amount)
        }
    }

}