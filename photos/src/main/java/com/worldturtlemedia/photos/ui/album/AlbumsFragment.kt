package com.worldturtlemedia.photos.ui.album

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BasicFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.databinding.FragmentAlbumsBinding
import com.worldturtlemedia.photos.databinding.FragmentAlbumsSelectionListBinding

class AlbumsFragment : BasicFragment(R.layout.fragment_albums) {

    private val binding by viewBinding(FragmentAlbumsBinding::bind)
}