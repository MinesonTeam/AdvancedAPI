plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    id("io.github.patrick.remapper") version "1.4.0"
}

// Library versions
val spigot = "1.20.4-R0.1-SNAPSHOT:remapped-mojang"
// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"

dependencies {
    compileOnly("org.spigotmc:spigot:$spigot")
    compileOnly(project(":bukkit"))
}

tasks {
    remap.get().version.set("1.20.4")
    compileJava.get().options.encoding = Charsets.UTF_8.name()
    javadoc.get().options.encoding = Charsets.UTF_8.name()
    processResources {
        filesNotMatching(listOf("**/*.png", "**/*.ogg", "**/models/**", "**/textures/**", "**/font/**.json", "**/plugin.yml")) {
            expand(mapOf(project.version.toString() to version))
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = Charsets.UTF_8.name()
    }
    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set(rootProject.name + "-${version}.jar")
        archiveClassifier.set("")
    }
    compileJava.get().dependsOn(clean)
    build.get().dependsOn(shadowJar)
}
