// Library versions
val spigot = property("spigot") as String
val bstats = property("bstats") as String
val hikaricp = property("hikaricp") as String
val jooq = property("jooq") as String
// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigot")
    compileOnly("org.bstats:bstats-bukkit:$bstats")
    compileOnly("com.zaxxer:HikariCP:$hikaricp")
    compileOnly("org.jooq:jooq:$jooq")
}
