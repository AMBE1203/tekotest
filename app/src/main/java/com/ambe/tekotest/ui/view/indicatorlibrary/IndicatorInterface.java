package com.ambe.tekotest.ui.view.indicatorlibrary;

import android.support.v4.view.ViewPager;

/**
 * Created by AMBE on 7/8/2019 at 14:08 PM.
 */
public interface IndicatorInterface {
    void setViewPager(ViewPager viewPager) throws PagesLessException;

    void setAnimateDuration(long duration);

    /**
     *
     * @param radius: radius in pixel
     */
    void setRadiusSelected(int radius);

    /**
     *
     * @param radius: radius in pixel
     */
    void setRadiusUnselected(int radius);

    /**
     *
     * @param distance: distance in pixel
     */
    void setDistanceDot(int distance);
}
