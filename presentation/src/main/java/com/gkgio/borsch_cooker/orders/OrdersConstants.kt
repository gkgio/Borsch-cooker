package com.gkgio.borsch_cooker.orders

class OrdersConstants {
    companion object {
        //OrdersType
        const val ORDERS_TYPE_ALL = "all"
        const val ORDERS_TYPE_ACTIVE = "active"

        //Statuses
        const val ORDERS_STATUS_CREATED = "created"
        const val ORDERS_STATUS_COMPLETED = "completed"
        const val ORDERS_STATUS_REJECTED = "rejected"
        const val ORDERS_STATUS_CANCELED = "canceled"
        const val ORDERS_STATUS_ACCEPTED = "accepted"
        const val ORDERS_STATUS_COOKING = "cooking"
        const val ORDERS_STATUS_CAN_PICKUP = "can_pickup"
        const val ORDERS_STATUS_DELIVERING = "delivering"

        //DeliveryType
        const val ORDERS_TAKE_DELIVERY = "delivery"
        const val ORDERS_TAKE_PICKUP = "pickup"
    }
}