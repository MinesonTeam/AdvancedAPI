pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "MinesonAPI"

include(
    ":api",
    ":bukkit",
    ":1_20_R3",
    ":1_21_R1",
)
