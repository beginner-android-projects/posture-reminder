package com.puntogris.posture.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.RealmClass
import kotlinx.parcelize.Parcelize

@Parcelize
@RealmClass(embedded = true)
open class Sound(
    var title: String = "",
    var uri: String = ""
    ): RealmObject(), Parcelable
