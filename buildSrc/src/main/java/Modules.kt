@Suppress("MemberVisibilityCanBePrivate")
object Modules {

    const val app = ":app"
    const val common = ":common"

    const val audioFull = ":audio_full"
    const val audioRaw = ":audio_raw"
    const val testFeature = ":test_feature"

    const val audioBase = ":audio_base"

    val dynamic = listOf(
        audioFull,
        audioRaw,
        testFeature
    )

    val all = arrayOf(
        app,
        common,
        audioBase,
        audioFull,
        audioRaw,
        testFeature
    )
}