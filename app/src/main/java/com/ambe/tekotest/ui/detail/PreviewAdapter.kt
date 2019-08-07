package com.ambe.tekotest.ui.detail

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ambe.tekotest.R
import com.ambe.tekotest.model.Images
import com.bumptech.glide.Glide

/**
 *  Created by AMBE on 7/8/2019 at 14:31 PM.
 */
class PreviewAdapter(
    context: Context,
    listImage: List<Images>
) : PagerAdapter() {

    private var context: Context? = context
    private var layoutInflater: LayoutInflater? = null
    private var listImage: List<Images>? = listImage

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {

        return listImage?.size!!

    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        val v = view as View
        container.removeView(v)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val images = listImage?.get(position)
        layoutInflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = layoutInflater!!.inflate(R.layout.item_image, null)
        val image = v.findViewById<ImageView>(R.id.img_product)
        Glide.with(context!!).load(images?.url).centerCrop()
            .into(image)

        val vp = container as ViewPager
        vp.addView(v, 0)
        return v
    }

    override fun getItemPosition(any: Any): Int {
        return POSITION_NONE
    }


}