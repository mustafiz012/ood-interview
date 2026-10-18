package shippinglocker

import shippinglocker.account.Account

fun interface NotificationInterface {
    fun sendNotification(message: String, user: Account)
}
