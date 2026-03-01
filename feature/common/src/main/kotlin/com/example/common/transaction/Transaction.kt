package com.example.common.transaction

import com.example.common.currency.Currency

data class Transaction(
    val id: String? = null,
    val amount: Double,
    val tipAmount: Double,
    val currency: Currency,
)
