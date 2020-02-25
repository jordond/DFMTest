package com.worldturtlemedia.photos.ui

import com.worldturtlemedia.dfmtest.common.base.viewbinding.BindingActivity
import com.worldturtlemedia.dfmtest.common.base.viewbinding.viewBinding
import com.worldturtlemedia.photos.databinding.ActivityGooglePhotosBinding
import com.worldturtlemedia.photos.databinding.ActivityGooglePhotosBinding.inflate

class GooglePhotosActivity : BindingActivity<ActivityGooglePhotosBinding>() {

    override val binding: ActivityGooglePhotosBinding by viewBinding { inflate(it) }

}
