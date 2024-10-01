group = "kz.hxncus.mc"
version = rootProject.version

// Library versions
val spigot = property("spigot") as String
val bstats = property("bstats") as String
val hikaricp = property("hikaricp") as String
val jooq = property("jooq") as String

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigot")
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")

    implementation("org.bstats:bstats-bukkit:$bstats")
    implementation("com.zaxxer:HikariCP:$hikaricp")
    implementation("org.jooq:jooq:$jooq")
}
