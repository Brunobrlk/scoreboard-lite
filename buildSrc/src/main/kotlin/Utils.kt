import org.gradle.api.Project

fun Project.getVersionCode(versionName: String?): Int {
    val version = versionName?.removePrefix("v")     // Allow vx.y.z
        ?.substringBefore('-')  // Allow -alpha | -beta | -rc
        ?: error("versionName is not set")

    val parts = version.split('.', limit = 3)

    val major = parts[0].toInt() * 1_000_000
    val minor = parts[1].toInt() * 1_000
    val patch = parts[2].toInt()

    println("Called: ${ major+minor+patch }")
    return major + minor + patch
}

fun Project.getTag(): String {

    val stdout = java.io.ByteArrayOutputStream()

    exec {
        commandLine("git", "describe", "--tags", "--abbrev=0")
        standardOutput = stdout
    }

    return stdout.toString().trim()
}

fun Project.getCustomVersionName(versionName: String, versionCode: Int): String {

    val appName = "scoreboard"
    val date = SimpleDateFormat("yyyy-MM-dd").format(Date())

    return "${appName}_${date}_v${versionName}-${versionCode}"
}
