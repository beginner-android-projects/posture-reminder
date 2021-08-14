package com.puntogris.posture.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import org.threeten.bp.LocalDate
import java.util.*

open class DayLog(
    @PrimaryKey
    var _id: ObjectId = ObjectId.get(),
    var _partition: String = "",
    var expCounter: Int = 0,
    var notificationsCounter: Int = 0,
    var exercisesCounter: Int = 0,
    var date: Date = Date()
): RealmObject()