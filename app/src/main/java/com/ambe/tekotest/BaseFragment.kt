package com.ambe.tekotest

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

/**
 *  Created by AMBE on 5/8/2019 at 17:28 PM.
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var navController: NavController


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navController = Navigation.findNavController(context as Activity, R.id.nav_host_fragment)
    }


}