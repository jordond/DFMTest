@Suppress("MemberVisibilityCanBePrivate")
object Modules {

    const val app = ":app"
    const val common = ":common"

    // TODO: These should be injected into the BuildConfig
    const val audioFull = ":audio_full"
    const val audioRaw = ":audio_raw"

    const val audioBase = ":audio_base"

    val dynamic = listOf(audioFull, audioRaw)

    val all = arrayOf(app, common, audioBase, audioFull, audioRaw)
}