package com.udacity.notepad.util

import android.content.Context
import android.view.LayoutInflater

/*
* Todo: Can't understand this statement
*       What is the purpose of this?
*       What is this val's type?
*       Where this(context) is come from?
*
* */
val Context.layoutInflater
    get() = LayoutInflater.from(this)

val Context.locationManager
    get() = this.getSystemService(Context.LOCATION_SERVICE)

val Context.packageName
    get() = this.packageName