package com.worldturtlemedia.dfmtest.ui.main

import androidx.activity.viewModels
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseActivity
import com.worldturtlemedia.dfmtest.features.FeatureManagerModel

class MainActivity : BaseActivity() {

    override fun layout(): Int = R.layout.main_activity

    private val featureMangerModel: FeatureManagerModel by viewModels()

    override fun afterCreate() {
        featureMangerModel.init(this)
    }
}
