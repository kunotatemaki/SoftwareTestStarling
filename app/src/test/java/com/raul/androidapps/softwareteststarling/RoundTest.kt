package com.raul.androidapps.softwareteststarling

import com.raul.androidapps.softwareteststarling.extensions.getPotentialSavings
import com.raul.androidapps.softwareteststarling.model.Money
import org.junit.Assert
import org.junit.Test


class RoundTest {


    private fun getMoney(money: Long, currency: String): Money = Money(currency = currency, minorUnits = money)

    private fun getGBPMoney(money: Float): Money =
        getMoney(money = (money * 100).toLong(), currency = "GBP")

    private fun getEURMoney(money: Float): Money =
        getMoney(money = (money * 100).toLong(), currency = "EUR")

    @Test
    fun roundTestGBP1() {
        val money = getGBPMoney(1.0F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 0L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "GBP")
    }

    @Test
    fun roundTestGBP2() {
        val money = getGBPMoney(1.01F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 99L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "GBP")
    }

    @Test
    fun roundTestGBP3() {
        val money = getGBPMoney(0.99F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 1L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "GBP")
    }

    @Test
    fun roundTestGBP4() {
        val money = getGBPMoney(0.50F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 50L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "GBP")
    }

    @Test
    fun roundTestEUR1() {
        val money = getEURMoney(31.32F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 68L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "EUR")
    }

    @Test
    fun roundTestEUR2() {
        val money = getEURMoney(1.91F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 9L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "EUR")
    }

    @Test
    fun roundTestEUR3() {
        val money = getEURMoney(0.99F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 1L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "EUR")
    }

    @Test
    fun roundTestEUR4() {
        val money = getEURMoney(0.51F)
        Assert.assertTrue(money.getPotentialSavings()?.minorUnits == 49L)
        Assert.assertTrue(money.getPotentialSavings()?.currency == "EUR")
    }


}
