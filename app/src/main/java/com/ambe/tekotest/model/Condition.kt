package com.ambe.tekotest.model

/**
 *  Created by AMBE on 6/8/2019 at 15:57 PM.
 */
data class Condition (val paymentMethods:List<String>, val orderValueMin:String, val orderValueMax:String,val coupon:String)