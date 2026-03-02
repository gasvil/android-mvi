package com.example.activation.data.mapper

import com.example.activation.data.model.InitializeResponse
import com.example.common.merchant.Merchant

fun InitializeResponse.toMerchant(): Merchant = Merchant(
    id = id,
    name = name
)

