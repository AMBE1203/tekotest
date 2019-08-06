package com.ambe.tekotest.ui.list

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.ambe.tekotest.R
import com.ambe.tekotest.helper.State
import com.ambe.tekotest.model.Products
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list_footer.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.DecimalFormat
import java.util.*

/**
 *  Created by AMBE on 6/8/2019 at 14:22 PM.
 */
class ProductsListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<Products, RecyclerView.ViewHolder>(ProductsDiffCallback) {

    private var state = State.LOADING

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Companion.DATA_VIEW_TYPE) ProductsViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Companion.DATA_VIEW_TYPE) {
            (holder as ProductsViewHolder).bind(getItem(position))
        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) Companion.DATA_VIEW_TYPE else Companion.FOOTER_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


    companion object {
        val ProductsDiffCallback = object : DiffUtil.ItemCallback<Products>() {
            override fun areItemsTheSame(p0: Products, p1: Products): Boolean {
                return p0.sku == p1.sku
            }

            override fun areContentsTheSame(p0: Products, p1: Products): Boolean {
                return p0 == p1
            }

        }
        private const val DATA_VIEW_TYPE = 1
        private const val FOOTER_VIEW_TYPE = 2
    }

    class ListFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(status: State?) {
            itemView.progress_bar.visibility =
                if (status == State.LOADING) VISIBLE else View.INVISIBLE
            itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
        }

        companion object {
            fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_footer, parent, false)
                view.txt_error.setOnClickListener { retry() }
                return ListFooterViewHolder(view)
            }
        }
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val formatter = DecimalFormat("#,###")

        fun bind(products: Products?) {
            if (products != null) {
                if (products.displayName != "") {
                    itemView.txt_product_name.text = products.displayName
                } else {
                    itemView.txt_product_name.text = products.name

                }

                if (products.images.isNotEmpty()) {

                    Glide.with(itemView.context).load(products.images[products.images.size - 1].url)
                        .centerCrop()
                        .into(itemView.img_product)
                }


                if (products.price.supplierSalePrice == products.price.sellPrice) {
                    itemView.txt_product_price_old.visibility = View.INVISIBLE
                    itemView.txt_per_sale.visibility = View.INVISIBLE
                    itemView.txt_product_price.text =
                        formatter.format(products.price.supplierSalePrice)
                } else {
                    itemView.txt_product_price_old.visibility = View.INVISIBLE
                    itemView.txt_per_sale.visibility = View.INVISIBLE
                }

            }
        }

        companion object {
            fun create(parent: ViewGroup): ProductsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                return ProductsViewHolder(view)
            }
        }
    }
}