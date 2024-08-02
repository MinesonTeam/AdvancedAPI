plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    id("io.github.patrick.remapper") version "1.4.0"
}

// Library versions
val spigot = property("spigot") as String
val bstats = property("bstats") as String
val hikaricp = property("hikaricp") as String
val jooq = property("jooq") as String
val apachemath3 = property("apachemath3") as String
val dsiutils = property("dsiutils") as String
// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigot")

    implementation("org.bstats:bstats-bukkit:$bstats")
    implementation("com.zaxxer:HikariCP:$hikaricp")
    implementation("org.jooq:jooq:$jooq")
    implementation("org.apache.commons:commons-math3:$apachemath3")
    implementation("it.unimi.dsi:dsiutils:$dsiutils")
}

tasks {
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
