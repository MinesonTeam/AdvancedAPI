plugins {
    id("java")
}

// Library versions
val spigot = property("spigot").toString()
val bstats = property("bstats").toString()
val hikaricp = property("hikaricp").toString()
val jooq = property("jooq").toString()

dependencies {
    compileOnly("org.spigotmc:spigot-api:$spigot-R0.1-SNAPSHOT")
    compileOnly("com.google.guava:guava:32.1.2-jre")
    compileOnly("commons-io:commons-io:2.15.1")

    implementation("org.bstats:bstats-bukkit:$bstats")
    implementation("com.zaxxer:HikariCP:$hikaricp")
    implementation("org.jooq:jooq:$jooq")

    testImplementation("org.spigotmc:spigot-api:$spigot-R0.1-SNAPSHOT")
    testImplementation("com.google.guava:guava:32.1.2-jre")
}
