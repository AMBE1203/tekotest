package com.ambe.tekotest.ui.list

import android.arch.paging.PagedListAdapter
import android.support.v4.text.HtmlCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
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
        return if (viewType == DATA_VIEW_TYPE) ProductsViewHolder.create(parent) else ListFooterViewHolder.create(
            retry,
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            (holder as ProductsViewHolder).bind(getItem(position))
            holder.itemView.setOnClickListener {
                if (iProductsListAdapterListner != null) {
                    iProductsListAdapterListner?.clickItem(getItem(position))
                }
            }
        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
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
                if (status == State.LOADING) VISIBLE else INVISIBLE
            itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else INVISIBLE
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

                if (products.objective.code == "gift") {
                    hideView(itemView.lnl_price_old, itemView.txt_unit)
                    itemView.txt_product_price.text =
                        itemView.context.getString(R.string.hang_qua_tang)

                } else {

                    if (products.status.sale == "ngung_kinh_doanh") {
                        hideView(itemView.lnl_price_old, itemView.txt_unit)

                        itemView.txt_product_price.text =
                            itemView.context.getString(R.string.nguong_kinh_doanh)

                    } else {
                        itemView.txt_unit.visibility = VISIBLE


                        if (products.price.supplierSalePrice == products.price.sellPrice) {
                            itemView.lnl_price_old.visibility = INVISIBLE

                            itemView.txt_product_price.text =
                                formatter.format(products.price.supplierSalePrice)

                        } else {
                            itemView.lnl_price_old.visibility = VISIBLE

                            itemView.txt_product_price.text =
                                formatter.format(products.price.sellPrice)
                            itemView.txt_product_price_old.text =
                                HtmlCompat.fromHtml(
                                    "<strike>${formatter.format(products.price.supplierSalePrice)}</strike>",
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                            val per =
                                (products.price.sellPrice - products.price.supplierSalePrice) * 100 / products.price.supplierSalePrice
                            itemView.txt_per_sale.text = "$per%"

                        }
                    }
                }


            }
        }

        private fun hideView(txt1: View, txt2: View) {
            txt1.visibility = INVISIBLE
            txt2.visibility = INVISIBLE
        }


        companion object {
            fun create(parent: ViewGroup): ProductsViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_product, parent, false)
                return ProductsViewHolder(view)
            }
        }
    }

    private var iProductsListAdapterListner: IProductsListAdapterListner? = null

    fun setListener(i: IProductsListAdapterListner) {
        this.iProductsListAdapterListner = i
    }

    interface IProductsListAdapterListner {
        fun clickItem(products: Products?)
    }
}