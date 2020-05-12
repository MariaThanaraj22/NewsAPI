@file:Suppress("DEPRECATION")

package com.maria.newsapi.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.maria.newsapi.fragment.DynamicFragment

import com.maria.newsapi.model.KotlinMainResponse

class TabAdapter(fm: FragmentManager?, var mNumOfTabs: Int, reponseData: KotlinMainResponse?) :
    FragmentStatePagerAdapter(fm!!) {

    var data: KotlinMainResponse? = reponseData

    override fun getItem(position: Int): Fragment {
        return DynamicFragment.addfrag(position, data)
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }


}