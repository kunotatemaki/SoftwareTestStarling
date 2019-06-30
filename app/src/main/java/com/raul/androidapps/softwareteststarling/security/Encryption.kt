@file:Suppress("DEPRECATION")

package com.raul.androidapps.softwareteststarling.security


interface Encryption {

    fun encryptString(text: String?, alias: String): String
    fun decryptString(text: String?, alias: String): String
}