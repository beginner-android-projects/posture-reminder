package com.puntogris.posture.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class RankingProfile(
    @PrimaryKey
    var _id: ObjectId = ObjectId.get(),
    var _partition: String = "",
    var name: String = "",
    var country: String = "",
    var experience: Int = 0
):RealmObject()