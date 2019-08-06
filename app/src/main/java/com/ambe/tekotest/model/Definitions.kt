package com.ambe.tekotest.model

/**
 *  Created by AMBE on 6/8/2019 at 16:02 PM.
 */
data class Definitions(
    val id: Int,
    val startedAt: String,
    val endedAt: String,
    val name: String,
    val type: String,
    val isDefault: Boolean,
    val partner: String,
    val description: String,
    val timeRanges: List<String>,
    val condition: Condition,
    val applyOn: String,
    val benefit: Benefit


)