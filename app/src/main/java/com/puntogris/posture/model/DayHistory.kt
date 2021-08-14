package com.puntogris.posture.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import org.threeten.bp.LocalDate

open class DayHistory(
    @PrimaryKey
    var _id: ObjectId = ObjectId.get(),
    var expGained: Int = 0,
    var notifications: Int = 0,
    var exercises: Int = 0,
    var date: String = LocalDate.now().toString()
): RealmObject()