package com.worldturtlemedia.photos.date

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.photos.databinding.FragmentDatePickerBinding

class DatePickerFragment : BindingFragment<FragmentDatePickerBinding>() {

    override val bindingInflater = binder { FragmentDatePickerBinding.inflate(it) }

}