package com.ambe.tekotest.model

/**
 *  Created by AMBE on 6/8/2019 at 11:51 AM.
 */

data class Products(
    var displayName: String,
    val color: Color,
    val tags: List<String>,
    val promotionPrices: List<PromotionPrices>,
    val promotions: List<Promotions>,
    val flashSales: List<String>,
    val attributeSet: AttributeSet,
    val categories: List<Categories>,
    val magentoId: Int,
    val seoInfo: SeoInfo,
    val rating: Rating,
    val allActiveFlashSales: List<String>,
    val sku: String,
    val name: String,
    val url: String,
    val brand: Brand,
    val status: Status,
    val objective: Objective,
    val productType: ProductType,
    val images: List<Images>,
    val price: Price,
    val productLine: ProductLine,
    val stocks: List<String>,
    val totalAvailable: Int
)