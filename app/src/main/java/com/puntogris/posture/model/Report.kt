package com.puntogris.posture.model

import androidx.annotation.Keep
import java.util.*

@Keep
class Report (
        val username:String,
        val email:String,
        val message:String,
        val date: Date = Date())