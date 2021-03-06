package com.puntogris.posture.model

sealed class RepoResult{
    object Success: RepoResult()
    object Failure: RepoResult()
}

sealed class ReminderUi{
    sealed class Item(val itemData: ItemData): ReminderUi() {
        class Interval(itemData: ItemData): Item(itemData)
        class Start(itemData: ItemData): Item(itemData)
        class End(itemData: ItemData): Item(itemData)
        class Days(itemData: ItemData): Item(itemData)
        class Sound(itemData: ItemData): Item(itemData)
        class Vibration(itemData: ItemData): Item(itemData)
    }
    class Name(var value: String = ""): ReminderUi()
    class Color(var color: Int): ReminderUi()
}

sealed class LoginResult {
    object InProgress: LoginResult()
    class Success(val userPrivateData: UserPrivateData): LoginResult()
    class Error(val errorMsg: String?): LoginResult()
}

sealed class UserAccount{
    object New: UserAccount()
    object Registered: UserAccount()
}