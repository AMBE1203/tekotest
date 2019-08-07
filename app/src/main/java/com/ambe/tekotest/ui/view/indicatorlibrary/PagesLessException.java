package com.ambe.tekotest.ui.view.indicatorlibrary;

/**
 * Created by AMBE on 7/8/2019 at 14:10 PM.
 */
public class PagesLessException extends Exception {
    @Override
    public String getMessage() {
        return "Pages must equal or larger than 2";
    }
}
