@Suppress("MemberVisibilityCanBePrivate")
object Modules {

    const val app = ":app"
    const val common = ":common"
    const val audio = ":audio"
    const val audioBase = ":audio_base"

    val dynamic = listOf(audio)

    val all = arrayOf(app, common, audio, audioBase)
}