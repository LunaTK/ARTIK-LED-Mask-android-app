package com.lunatk.ledmaskapp.extension

import java.text.SimpleDateFormat
import java.util.*

val Date.simple: String
    get() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(this)
    }