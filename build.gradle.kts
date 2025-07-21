plugins {
    id("java")
    id("maven-publish")
    id("io.github.goooler.shadow") version "8.1.8"
}
// Project version and group
version = property("projectVersion").toString()
group = "kz.hxncus.mc"
// Library versions
val junit = property("junit").toString()
val lombok = property("lombok").toString()
allprojects {
    apply(plugin = "java")
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Spigot
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.codemc.io/repository/maven-public/")
        mavenLocal()
    }
    dependencies {
        compileOnly("org.projectlombok:lombok:$lombok")
        annotationProcessor("org.projectlombok:lombok:$lombok")

        testAnnotationProcessor("org.projectlombok:lombok:$lombok")
        testCompileOnly("org.projectlombok:lombok:$lombok")
        testImplementation("org.junit.jupiter:junit-jupiter:$junit")
        testImplementation("org.mockito:mockito-core:4.11.0")
    }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
    }
}
dependencies {
    implementation(project(":bukkit"))
}
val projectNameLC = rootProject.name.lowercase();
val projectGroup = rootProject.group.toString();
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
        minimize {
            exclude(project(":bukkit"))
        }
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
