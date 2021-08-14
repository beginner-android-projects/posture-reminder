package com.puntogris.posture.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class User(
    @PrimaryKey
    var _id: ObjectId = ObjectId(),
    var name: String = "",
    var userId: String = "",
    var experience: Int = 0,
    var currentReminderId: Int = 0,
    var creation: Date = Date(),
    var email: String = ""
): RealmObject()