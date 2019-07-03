package com.raul.androidapps.softwareteststarling.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.raul.androidapps.softwareteststarling.model.Money
import java.lang.ref.WeakReference
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.Normalizer
import kotlin.math.ceil
import kotlin.math.roundToLong

fun String.normalizedString(): String {
    val normalized: String = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalized.replace("[^\\p{ASCII}]".toRegex(), "").trim { it <= ' ' }.toLowerCase()
}

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>) = Transformations.switchMap(this, func)

fun <T> WeakReference<T>.safe(body: T.() -> Unit) {
    this.get()?.body()
}

fun Money.getPotentialSavings(): Money? =
    when (currency) {
        "GBP", "EUR" -> {
            val majorUnits = minorUnits.toDouble() / 100
            val majorUnitsRounded = ceil(majorUnits)
            val minorUnitsRounded = (majorUnitsRounded * 100).toLong()
            Money(currency = currency, minorUnits = minorUnitsRounded - minorUnits)
        }

        else -> null //Todo implement other currencies
    }

fun Float.getValueWithTwoDecimalsPrecisionInStringFormat(): String {
    val df = DecimalFormat("#.##")
    return df.format(this)
}

fun <T> LiveData<T>.getDistinct(): LiveData<T> {
    val distinctLiveData = MediatorLiveData<T>()
    distinctLiveData.addSource(this, object : Observer<T> {
        private var initialized = false
        private var lastObj: T? = null
        override fun onChanged(obj: T?) {
            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null)
                || obj != lastObj
            ) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}