package com.ambe.tekotest.model

/**
 *  Created by AMBE on 6/8/2019 at 16:18 PM.
 */
data class Items(
    val id: Int,
    val sku: String,
    val name: String,
    val quantity: Int,
    val budgetId: Int,
    val budgetStatus: String,
    val outOfBudget: Boolean
)