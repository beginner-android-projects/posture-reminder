package com.puntogris.posture.model

class SettingItem(val code: SettingUi? = null, val title: String = "", var summary: String= "")

enum class SettingUi{
    Name,
    Theme,
    BatteryOpt,
    Help,
    Ticket,
    Website,
    Github,
    RateApp,
    Version
}