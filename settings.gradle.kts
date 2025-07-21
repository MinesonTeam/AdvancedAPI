rootProject.name = "AdvancedAPI"
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
include(
    ":bukkit",
)
