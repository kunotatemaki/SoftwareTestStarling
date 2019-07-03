package com.raul.androidapps.softwareteststarling

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun InputStream.convertToString(): String {
    val reader = BufferedReader(InputStreamReader(this))
    val sb = StringBuilder()

    var line: String? = reader.readLine()
    try {
        while (line != null) {
            sb.append(line).append('\n')
            line = reader.readLine()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            this.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return sb.toString()
}

fun <T> LiveData<T>.getValueBlockingForUnitTests(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)
    val innerObserver = Observer<T> {
        value = it
        latch.countDown()
    }
    observeForever(innerObserver)
    latch.await(2, TimeUnit.SECONDS)
    return value
}