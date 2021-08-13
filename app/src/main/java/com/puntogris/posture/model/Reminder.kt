package com.puntogris.posture.model

import android.os.Parcel
import android.os.Parcelable
import com.puntogris.posture.R
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.bson.types.ObjectId

fun Parcel.readStringRealmList(): RealmList<Int>? = when {
    readInt() > 0 -> RealmList<Int>().also { list ->
        repeat(readInt()) {
            list.add(readInt())
        }
    }
    else -> null
}

fun Parcel.writeStringRealmList(realmList: RealmList<Int>?) {
    writeInt(
        when (realmList) {
            null -> 0
            else -> 1
        }
    )
    if (realmList != null) {
        writeInt(realmList.size)
        for (t in realmList) {
            writeInt(t)
        }
    }
}

object StringRealmListParceler: Parceler<RealmList<Int>?> {
    override fun create(parcel: Parcel): RealmList<Int>? = parcel.readStringRealmList()

    override fun RealmList<Int>?.write(parcel: Parcel, flags: Int) {
        parcel.writeStringRealmList(this)
    }
}

@Parcelize
open class Reminder(
    @PrimaryKey var _id: ObjectId = ObjectId(),
    var userId: String = "",
    var name: String = "",
    var timeInterval: Int = 0,
    var startTime: Int = 0,
    var endTime: Int = 0,
    @Required
    var alarmDays: @WriteWith<StringRealmListParceler> RealmList<Int>? = RealmList<Int>(),
    var color: Int = R.color.grey,
    var vibrationPattern: Int = 0,
    var sound: Sound? = Sound()
):RealmObject(), Parcelable{

    fun alarmNotPastMidnight() = startTime < endTime

    fun alarmDaysSummary(daysList: Array<String>) =
         alarmDays?.joinToString(", ") {
            daysList[it].first().toString()
         }

    fun timeIntervalSummary(): String{
        return if (timeInterval < 60){
            "$timeInterval m."
        }else{
            "${timeInterval/60} h. ${timeInterval % 60} m."
        }
    }

    fun alarmPastMidnightAndOutOfRange(minutesSinceMidnight: Int) =
       minutesSinceMidnight in (endTime + 1) until startTime

}