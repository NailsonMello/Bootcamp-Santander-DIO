package com.nailson.todolist.santander.bootcamp.extensions

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun Date.format() : String {
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

fun Context.myToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

var TextInputLayout.text : String
    get() = editText?.text?.toString() ?: ""
    set(value) {
        editText?.setText(value)
    }