plugins {
    id("java")
    id("maven-publish")
    id("io.github.goooler.shadow") version "8.1.8"
}

// Library versions
val junit: String = property("junit") as String
val lombok: String = property("lombok") as String
// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"

allprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
        maven("https://papermc.io/repo/repository/maven-public/") // Paper
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
        maven("https://oss.sonatype.org/content/groups/public/")
        mavenLocal()
    }
    dependencies {
        compileOnly("org.projectlombok:lombok:$lombok")
        compileOnly(fileTree("../libs/compileOnly/"))

        implementation(fileTree("../libs/implementation/"))

        annotationProcessor("org.projectlombok:lombok:$lombok")
        testAnnotationProcessor("org.projectlombok:lombok:$lombok")
        testCompileOnly("org.projectlombok:lombok:$lombok")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junit")
    }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(project(":bukkit"))
}

val projectNameLC: String = rootProject.name.lowercase();
val projectGroup: String = rootProject.group.toString();

tasks {
    compileJava.get().options.encoding = Charsets.UTF_8.name()
    javadoc.get().options.encoding = Charsets.UTF_8.name()
    processResources {
        filesMatching("**/plugin.yml") {
            expand("version" to rootProject.version, "name" to rootProject.name)
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        filteringCharset = Charsets.UTF_8.name()
    }
    shadowJar {
        archiveClassifier.set("")
        archiveFileName.set(rootProject.name + "-${version}.jar")
        archiveClassifier.set("")
    }
}

tasks.compileJava {
    dependsOn(tasks.clean)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "kz.hxncus.mc"
            artifactId = "advanced-api"
            version = rootProject.version.toString()
            from(components["java"])
        }
    }
}
