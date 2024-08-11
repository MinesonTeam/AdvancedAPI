import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("java")
    id("maven-publish")
    id("io.github.goooler.shadow") version "8.1.7"
}

val nmsVersionList: List<String> = listOf(
    "1_20_R3"
)
// Library versions
val junit = property("junit") as String
val lombok = property("lombok") as String
// Project version and group
version = property("projectVersion") as String
group = "kz.hxncus.mc"
val projectNameLC = rootProject.name.lowercase(Locale.ENGLISH);
val projectGroup = rootProject.group.toString();

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "io.github.goooler.shadow")
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.triumphteam.dev/snapshots")
        maven("https://libraries.minecraft.net/") // Minecraft repo
        maven("https://maven.enginehub.org/repo/")
        maven("https://jitpack.io") // JitPack
        maven("https://papermc.io/repo/repository/maven-public/") // Paper
    }
    tasks {
        processResources {
            filesMatching("**/plugin.yml") {
                expand("version" to rootProject.version, "name" to rootProject.name)
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            filteringCharset = Charsets.UTF_8.name()
        }
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
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(path = ":bukkit", configuration = "shadow"))
    nmsVersionList.forEach { implementation(project(path = ":${it}", configuration = "reobf")) }
}

tasks {
    compileJava.get().options.encoding = Charsets.UTF_8.name()
    javadoc.get().options.encoding = Charsets.UTF_8.name()
    shadowJar {
        nmsVersionList.forEach { dependsOn(":${it}:remap") }
        archiveClassifier.set("")
        relocate("com", "$projectGroup.$projectNameLC.libs.com")
        relocate("javax", "$projectGroup.$projectNameLC.libs.javax")
        relocate("org.bstats", "$projectGroup.$projectNameLC.metrics")
        relocate("org.jooq", "$projectGroup.$projectNameLC.libs.jooq")
        relocate("migrations", "$projectGroup.$projectNameLC.libs.jooq")
        relocate("org.reactivestreams", "$projectGroup.$projectNameLC.libs.reactivestreams")
        relocate("org.slf4j", "$projectGroup.$projectNameLC.libs.slf4j")
        relocate("xsd", "$projectGroup.$projectNameLC.libs.jooq")
        manifest {
            attributes(
                mapOf(
                    "Built-By" to System.getProperty("user.name"),
                    "Version" to version,
                    "Build-Timestamp" to SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSZ").format(Date.from(Instant.now())),
                    "Created-By" to "Gradle ${gradle.gradleVersion}",
                    "Build-Jdk" to "${System.getProperty("java.version")} ${System.getProperty("java.vendor")} ${
                        System.getProperty(
                            "java.vm.version"
                        )
                    }",
                    "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${
                        System.getProperty(
                            "os.version"
                        )
                    }",
                    "Compiled" to (project.findProperty("compiled")?.toString() ?: "true").toBoolean()
                )
            )
        }
        archiveFileName.set(rootProject.name + "-${version}.jar")
        archiveClassifier.set("")
    }
    compileJava.get().dependsOn(clean)
    build.get().dependsOn(shadowJar)
}
