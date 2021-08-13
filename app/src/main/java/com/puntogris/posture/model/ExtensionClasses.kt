package com.puntogris.posture.model

sealed class RepoResult{
    object InProgress: RepoResult()
    object Success: RepoResult()
    object Error: RepoResult()
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

sealed class AuthState {
    object AuthRequired : AuthState()
    object AuthComplete : AuthState()
}
sealed class LoginResult {
    object InProgress: LoginResult()
    object Success: LoginResult()
    class Error(val errorMsg: String?): LoginResult()
}