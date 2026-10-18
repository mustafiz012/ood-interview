package restaurant.reservation

import restaurant.menu.MenuItem

class OrderItem(val item: MenuItem) {
    var status: Status = Status.PENDING
        private set

    fun sendToKitchen() {
        if (status == Status.PENDING) status = Status.SENT_TO_KITCHEN
    }

    fun deliverToCustomer() {
        if (status == Status.SENT_TO_KITCHEN) status = Status.DELIVERED
    }

    fun cancel() {
        if (status == Status.PENDING || status == Status.SENT_TO_KITCHEN) {
            status = Status.CANCELED
        }
    }
}
