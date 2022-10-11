package com.example.currencyexchanger.data.converter

import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.models.data.ResponseEntity
import java.lang.IllegalStateException
import kotlin.math.roundToLong

/**
 * @author Bulat Bagaviev
 * @created 09.10.2022
 */

class EntityConverter {

    fun convert(input: ResponseEntity?): ExchangeModel {

        if (input == null)
            throw IllegalStateException("Empty response!")

        val rates = arrayListOf<NormalRate>()
        val base = input.base
        val date = input.date
        val success = input.success

        rates.add(NormalRate("AED", input.rates.aED))
        rates.add(NormalRate("AFN", input.rates.aFN))
        rates.add(NormalRate("ALL", input.rates.aLL))
        rates.add(NormalRate("AMD", input.rates.aMD))
        rates.add(NormalRate("ANG", input.rates.aNG))
        rates.add(NormalRate("AOA", input.rates.aOA))
        rates.add(NormalRate("ARS", input.rates.aRS))
        rates.add(NormalRate("AUD", input.rates.aUD))
        rates.add(NormalRate("AWG", input.rates.aWG))
        rates.add(NormalRate("AZN", input.rates.aZN))
        rates.add(NormalRate("BAM", input.rates.bAM))
        rates.add(NormalRate("BBD", input.rates.bBD))
        rates.add(NormalRate("BDT", input.rates.bDT))
        rates.add(NormalRate("BGN", input.rates.bGN))
        rates.add(NormalRate("BHD", input.rates.bHD))
        rates.add(NormalRate("BIF", input.rates.bIF))
        rates.add(NormalRate("BMD", input.rates.bMD))
        rates.add(NormalRate("BND", input.rates.bND))
        rates.add(NormalRate("BOB", input.rates.bOB))
        rates.add(NormalRate("BRL", input.rates.bRL))
        rates.add(NormalRate("BSD", input.rates.bSD))
        rates.add(NormalRate("BTC", input.rates.bTC))
        rates.add(NormalRate("BTN", input.rates.bTN))
        rates.add(NormalRate("BWP", input.rates.bWP))
        rates.add(NormalRate("BYN", input.rates.bYN))
        rates.add(NormalRate("BYR", input.rates.bYR))
        rates.add(NormalRate("BZD", input.rates.bZD))
        rates.add(NormalRate("CAD", input.rates.cAD))
        rates.add(NormalRate("CDF", input.rates.cDF))
        rates.add(NormalRate("CHF", input.rates.cHF))
        rates.add(NormalRate("CLF", input.rates.cLF))
        rates.add(NormalRate("CLP", input.rates.cLP))
        rates.add(NormalRate("CNY", input.rates.cNY))
        rates.add(NormalRate("COP", input.rates.cOP))
        rates.add(NormalRate("CRC", input.rates.cRC))
        rates.add(NormalRate("CUC", input.rates.cUC))
        rates.add(NormalRate("CUP", input.rates.cUP))
        rates.add(NormalRate("CVE", input.rates.cVE))
        rates.add(NormalRate("CZK", input.rates.cZK))
        rates.add(NormalRate("DJF", input.rates.dJF))
        rates.add(NormalRate("DKK", input.rates.dKK))
        rates.add(NormalRate("DOP", input.rates.dOP))
        rates.add(NormalRate("DZD", input.rates.dZD))
        rates.add(NormalRate("EGP", input.rates.eGP))
        rates.add(NormalRate("ERN", input.rates.eRN))
        rates.add(NormalRate("ETB", input.rates.eTB))
        rates.add(NormalRate("EUR", input.rates.eUR))
        rates.add(NormalRate("FJD", input.rates.fJD))
        rates.add(NormalRate("FKP", input.rates.fKP))
        rates.add(NormalRate("GBP", input.rates.gBP))
        rates.add(NormalRate("GEL", input.rates.gEL))
        rates.add(NormalRate("GGP", input.rates.gGP))
        rates.add(NormalRate("GHS", input.rates.gHS))
        rates.add(NormalRate("GIP", input.rates.gIP))
        rates.add(NormalRate("GMD", input.rates.gMD))
        rates.add(NormalRate("GNF", input.rates.gNF))
        rates.add(NormalRate("GTQ", input.rates.gTQ))
        rates.add(NormalRate("GYD", input.rates.gYD))
        rates.add(NormalRate("HKD", input.rates.hKD))
        rates.add(NormalRate("HNL", input.rates.hNL))
        rates.add(NormalRate("HRK", input.rates.hRK))
        rates.add(NormalRate("HTG", input.rates.hTG))
        rates.add(NormalRate("HUF", input.rates.hUF))
        rates.add(NormalRate("IDR", input.rates.iDR))
        rates.add(NormalRate("ILS", input.rates.iLS))
        rates.add(NormalRate("IMP", input.rates.iMP))
        rates.add(NormalRate("INR", input.rates.iNR))
        rates.add(NormalRate("IQD", input.rates.iQD))
        rates.add(NormalRate("IRR", input.rates.iRR))
        rates.add(NormalRate("ISK", input.rates.iSK))
        rates.add(NormalRate("JEP", input.rates.jEP))
        rates.add(NormalRate("JMD", input.rates.jMD))
        rates.add(NormalRate("JOD", input.rates.jOD))
        rates.add(NormalRate("JPY", input.rates.jPY))
        rates.add(NormalRate("KES", input.rates.kES))
        rates.add(NormalRate("KGS", input.rates.kGS))
        rates.add(NormalRate("KHR", input.rates.kHR))
        rates.add(NormalRate("KMF", input.rates.kMF))
        rates.add(NormalRate("KPW", input.rates.kPW))
        rates.add(NormalRate("KRW", input.rates.kRW))
        rates.add(NormalRate("KWD", input.rates.kWD))
        rates.add(NormalRate("KYD", input.rates.kYD))
        rates.add(NormalRate("KZT", input.rates.kZT))
        rates.add(NormalRate("LAK", input.rates.lAK))
        rates.add(NormalRate("LBP", input.rates.lBP))
        rates.add(NormalRate("LKR", input.rates.lKR))
        rates.add(NormalRate("LRD", input.rates.lRD))
        rates.add(NormalRate("LSL", input.rates.lSL))
        rates.add(NormalRate("LTL", input.rates.lTL))
        rates.add(NormalRate("LVL", input.rates.lVL))
        rates.add(NormalRate("LYD", input.rates.lYD))
        rates.add(NormalRate("MAD", input.rates.mAD))
        rates.add(NormalRate("MDL", input.rates.mDL))
        rates.add(NormalRate("MGA", input.rates.mGA))
        rates.add(NormalRate("MKD", input.rates.mKD))
        rates.add(NormalRate("MMK", input.rates.mMK))
        rates.add(NormalRate("MNT", input.rates.mNT))
        rates.add(NormalRate("MOP", input.rates.mOP))
        rates.add(NormalRate("MRO", input.rates.mRO))
        rates.add(NormalRate("MUR", input.rates.mUR))
        rates.add(NormalRate("MVR", input.rates.mVR))
        rates.add(NormalRate("MWK", input.rates.mWK))
        rates.add(NormalRate("MXN", input.rates.mXN))
        rates.add(NormalRate("MYR", input.rates.mYR))
        rates.add(NormalRate("MZN", input.rates.mZN))
        rates.add(NormalRate("NAD", input.rates.nAD))
        rates.add(NormalRate("NGN", input.rates.nGN))
        rates.add(NormalRate("NIO", input.rates.nIO))
        rates.add(NormalRate("NOK", input.rates.nOK))
        rates.add(NormalRate("NPR", input.rates.nPR))
        rates.add(NormalRate("NZD", input.rates.nZD))
        rates.add(NormalRate("OMR", input.rates.oMR))
        rates.add(NormalRate("PAB", input.rates.pAB))
        rates.add(NormalRate("PEN", input.rates.pEN))
        rates.add(NormalRate("PGK", input.rates.pGK))
        rates.add(NormalRate("PHP", input.rates.pHP))
        rates.add(NormalRate("PKR", input.rates.pKR))
        rates.add(NormalRate("PLN", input.rates.pLN))
        rates.add(NormalRate("PYG", input.rates.pYG))
        rates.add(NormalRate("QAR", input.rates.qAR))
        rates.add(NormalRate("RON", input.rates.rON))
        rates.add(NormalRate("RSD", input.rates.rSD))
        rates.add(NormalRate("RUB", input.rates.rUB))
        rates.add(NormalRate("RWF", input.rates.rWF))
        rates.add(NormalRate("SAR", input.rates.sAR))
        rates.add(NormalRate("SBD", input.rates.sBD))
        rates.add(NormalRate("SCR", input.rates.sCR))
        rates.add(NormalRate("SDG", input.rates.sDG))
        rates.add(NormalRate("SEK", input.rates.sEK))
        rates.add(NormalRate("SGD", input.rates.sGD))
        rates.add(NormalRate("SHP", input.rates.sHP))
        rates.add(NormalRate("SLL", input.rates.sLL))
        rates.add(NormalRate("SOS", input.rates.sOS))
        rates.add(NormalRate("SRD", input.rates.sRD))
        rates.add(NormalRate("STD", input.rates.sTD))
        rates.add(NormalRate("SVC", input.rates.sVC))
        rates.add(NormalRate("SYP", input.rates.sYP))
        rates.add(NormalRate("SZL", input.rates.sZL))
        rates.add(NormalRate("THB", input.rates.tHB))
        rates.add(NormalRate("TJS", input.rates.tJS))
        rates.add(NormalRate("TMT", input.rates.tMT))
        rates.add(NormalRate("TND", input.rates.tND))
        rates.add(NormalRate("TOP", input.rates.tOP))
        rates.add(NormalRate("TRY", input.rates.tRY))
        rates.add(NormalRate("TTD", input.rates.tTD))
        rates.add(NormalRate("TWD", input.rates.tWD))
        rates.add(NormalRate("TZS", input.rates.tZS))
        rates.add(NormalRate("UAH", input.rates.uAH))
        rates.add(NormalRate("UGX", input.rates.uGX))
        rates.add(NormalRate("USD", input.rates.uSD))
        rates.add(NormalRate("UYU", input.rates.uYU))
        rates.add(NormalRate("UZS", input.rates.uZS))
        rates.add(NormalRate("VND", input.rates.vND))
        rates.add(NormalRate("VUV", input.rates.vUV))
        rates.add(NormalRate("WST", input.rates.wST))
        rates.add(NormalRate("XAF", input.rates.xAF))
        rates.add(NormalRate("XAG", input.rates.xAG))
        rates.add(NormalRate("XAU", input.rates.xAU))
        rates.add(NormalRate("XCD", input.rates.xCD))
        rates.add(NormalRate("XDR", input.rates.xDR))
        rates.add(NormalRate("XOF", input.rates.xOF))
        rates.add(NormalRate("XPF", input.rates.xPF))
        rates.add(NormalRate("YER", input.rates.yER))
        rates.add(NormalRate("ZAR", input.rates.zAR))
        rates.add(NormalRate("ZMK", input.rates.zMK))
        rates.add(NormalRate("ZMW", input.rates.zMW))
        rates.add(NormalRate("ZWL", input.rates.zWL))

        return ExchangeModel(base, date, success, rates)
    }
}