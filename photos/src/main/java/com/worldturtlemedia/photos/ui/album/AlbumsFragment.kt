package com.worldturtlemedia.photos.ui.album

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.photos.databinding.FragmentAlbumsBinding

class AlbumsFragment : BindingFragment<FragmentAlbumsBinding>() {

    override val bindingInflater = binder { FragmentAlbumsBinding.inflate(it) }

}