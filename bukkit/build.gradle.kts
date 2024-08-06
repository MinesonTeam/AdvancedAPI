plugins {
    id("java")
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
    compileOnly(project(":api"))

    implementation("org.bstats:bstats-bukkit:$bstats")
    implementation("com.zaxxer:HikariCP:$hikaricp")
    implementation("org.jooq:jooq:$jooq")
}

tasks {
    compileJava.get().options.encoding = Charsets.UTF_8.name()
    javadoc.get().options.encoding = Charsets.UTF_8.name()
    processResources {
        filesNotMatching(
            listOf(
                "**/*.png",
                "**/*.ogg",
                "**/models/**",
                "**/textures/**",
                "**/font/**.json",
                "**/plugin.yml"
            )
        ) {
            expand(mapOf(project.version.toString() to version))
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = Charsets.UTF_8.name()
    }
    compileJava.get().dependsOn(clean)
}
