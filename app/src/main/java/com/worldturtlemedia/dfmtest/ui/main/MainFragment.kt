package com.worldturtlemedia.dfmtest.ui.main

import androidx.fragment.app.viewModels
import com.worldturtlemedia.dfmtest.R
import com.worldturtlemedia.dfmtest.common.base.BaseFragment

class MainFragment : BaseFragment() {

    override fun layout(): Int = R.layout.main_fragment

    private val viewModel: MainViewModel by viewModels()
}
