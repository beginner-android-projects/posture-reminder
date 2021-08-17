package com.puntogris.posture.utils

object Constants {
    // 720 are minutes after midnight, so 12am
    const val DEFAULT_START_TIME_PERIOD = 720
    // 0 are minutes after midnight, so 12pm
    const val DEFAULT_END_TIME_PERIOD = 0
    const val DEFAULT_INTERVAL_REPEATING = 60 //minutes
    const val DEFAULT_SHOW_NOTIFICATIONS_PREF_VALUE = true
    const val DAILY_ALARM_TRIGGERED = "DAILY_ALARM_TRIGGERED"
    const val REPEATING_ALARM_TRIGGERED = "REPEATING_ALARM_TRIGGERED"
    const val POSTURE_NOTIFICATION_ID = "postureNotification"
    const val TICKET_COLLECTION_NAME = "tickets"
    const val SHAKE_ANIMATION_DURATION = 800L

    //Notification channel
    const val CHANNEL_NAME = "notification posture"
    const val CHANNEL_DESCRIPTION = "channel for posture notification"

    //Keys
    const val TIME_UNIT_KEY = "time_unit_key"
    const val INTERVAL_KEY = "interval_key"
    const val DATA_KEY = "data_key"
    const val VIBRATION_PICKER_KEY = "vibration_picker_key"
    const val SOUND_PICKER_KEY = "sound_picker_key"

    //Pref keys
    const val APP_PREFERENCES_NAME = "app_preferences"
    const val LAST_VERSION_CODE = "last_version_code"
    const val APP_THEME = "pref_app_theme"
    const val PANDA_ANIMATION = "pref_panda_animation"



}