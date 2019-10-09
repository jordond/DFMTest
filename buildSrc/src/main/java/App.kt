object App {

    private const val major: Int = 1
    private const val minor: Int = 0
    private const val patch: Int = 0
    private const val suffix: String = "dev"

    const val rootName = "DFM Test"

    const val name = "$major.$minor.$patch${suffix}"
    const val code = major * 10000 + minor * 1000 + patch

    const val compileSdk: Int = 28
    const val targetSdk: Int = compileSdk
    const val minSdk: Int = 21

    const val applicationId: String = "com.worldturtlemedia.dfmtest"
}