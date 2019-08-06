package com.ambe.tekotest.model

/**
 *  Created by AMBE on 6/8/2019 at 15:58 PM.
 */
data class Money(val id:Int, val money: Int, val percent:String, val maxDiscount:String,val discountType:String, val budgetId:Int, val budgetStatus:String, val outOfBudget:Boolean)