package com.worldturtlemedia.photos.album

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.binder
import com.worldturtlemedia.photos.databinding.FragmentAlbumsSelectionListBinding

class AlbumSelectionListFragment : BindingFragment<FragmentAlbumsSelectionListBinding>() {

    override val bindingInflater = binder { FragmentAlbumsSelectionListBinding.inflate(it) }

}