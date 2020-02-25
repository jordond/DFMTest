package com.worldturtlemedia.photos.ui.date

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BasicFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.databinding.FragmentAlbumsSelectionListBinding
import com.worldturtlemedia.photos.databinding.FragmentDatePickerBinding

class DatePickerFragment : BasicFragment(R.layout.fragment_date_picker) {

    private val binding by viewBinding(FragmentDatePickerBinding::bind)
}