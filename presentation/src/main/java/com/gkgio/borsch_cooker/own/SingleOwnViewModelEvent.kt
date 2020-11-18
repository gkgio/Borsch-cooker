package com.gkgio.borsch_cooker.own

sealed class SingleOwnViewModelEvent {
    object HelpStatus : SingleOwnViewModelEvent()
    object HelpDelivery : SingleOwnViewModelEvent()
    object BuyContainers : SingleOwnViewModelEvent()
}