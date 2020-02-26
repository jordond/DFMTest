package com.worldturtlemedia.photos.album

import com.worldturtlemedia.dfmtest.common.base.viewbinding.LayoutFragment
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.photos.R
import com.worldturtlemedia.photos.databinding.FragmentAlbumsSelectionListBinding

class AlbumSelectionListFragment : LayoutFragment(R.layout.fragment_albums_selection_list) {

    private val binding by viewBinding(FragmentAlbumsSelectionListBinding::bind)
}