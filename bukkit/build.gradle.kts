group = "kz.hxncus.mc"
version = rootProject.version

// Library versions
val spigot = property("spigot") as String
val bstats = property("bstats") as String
val hikaricp = property("hikaricp") as String
val jooq = property("jooq") as String

subprojects {
    dependencies {
        compileOnly(project(":api"))
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigot")

    implementation("org.bstats:bstats-bukkit:$bstats")
    implementation("com.zaxxer:HikariCP:$hikaricp")
    implementation("org.jooq:jooq:$jooq")
}
