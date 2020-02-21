package com.worldturtlemedia.photos.date

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.photos.databinding.FragmentDateSelectionListBinding

class DateSelectionListFragment : BindingFragment<FragmentDateSelectionListBinding>() {

    override val bindingInflater = binder { FragmentDateSelectionListBinding.inflate(it) }

}