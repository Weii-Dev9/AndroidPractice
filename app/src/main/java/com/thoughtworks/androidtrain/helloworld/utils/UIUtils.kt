package com.thoughtworks.androidtrain.helloworld.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

public class UIUtils {
    companion object {
        public fun replaceFragment(
            fragmentManager: FragmentManager,
            fragment: Fragment,
            fragmentId: Int,
            tag: String?
        ) {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(fragmentId, fragment, tag);
            transaction.addToBackStack(tag)
            transaction.commitAllowingStateLoss()
        }
    }
}