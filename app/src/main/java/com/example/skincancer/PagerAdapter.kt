package com.example.skincancer

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private val TABTITLES = arrayOf("+SkinCancer", "ListSkinCancer", "EditSkinCancer", "DeleteSkinCancer", "SearchSkinCancerdates", "ImageRecognition")
    }

    override fun getItem(position: Int): Fragment {
        // instantiate a fragment for the page.
        if (position == 0) {
            return CreateSkinCancerFragment.newInstance(mContext)    } 
        else if (position == 1) {
            return ListSkinCancerFragment.newInstance(mContext)    } 
        else if (position == 2) {
            return EditSkinCancerFragment.newInstance(mContext)    } 
        else if (position == 3) {
            return DeleteSkinCancerFragment.newInstance(mContext)    } 
        else if (position == 4) {
            return SearchSkinCancerdatesFragment.newInstance(mContext)    } 
        else if (position == 5) {
            return ImageRecognitionFragment.newInstance(mContext)    } 
        return CreateSkinCancerFragment.newInstance(mContext) 
    }

    override fun getPageTitle(position: Int): CharSequence {
        return TABTITLES[position]
    }

    override fun getCount(): Int {
        return 6
    }
}
