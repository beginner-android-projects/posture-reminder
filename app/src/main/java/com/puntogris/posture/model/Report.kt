package com.puntogris.posture.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class Report (
    @PrimaryKey
    var _id: ObjectId = ObjectId.get(),
    var username:String = "",
    var email:String = "",
    var message:String = "",
    var date: Date = Date()
): RealmObject()