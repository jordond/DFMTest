package com.worldturtlemedia.photos.album

import com.worldturtlemedia.dfmtest.common.base.viewbinding.LayoutFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.databinding.FragmentAlbumsBinding

class AlbumsFragment : LayoutFragment(R.layout.fragment_albums) {

    private val binding by viewBinding(FragmentAlbumsBinding::bind)
}