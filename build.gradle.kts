import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.21"
    id("fabric-loom") version "1.7.1"
    id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 17
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("mysticrealms") {
            sourceSet("main")
            sourceSet("client")
        }
    }
}

repositories {
    maven {
        name = "0mods mavenReleases"
        url = uri("https://maven.0mods.team/releases")
    }
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://maven.parchmentmc.org")
    maven("https://maven.blamejared.com")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.architectury.dev/")
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.0mods.team/releases")
    maven("https://jitpack.io")
    maven("https://maven.neoforged.net/releases")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.cleanroommc.com")
    mavenCentral()
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")

    //hollowcore

    val imguiVersion: String by project

    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-app:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-binding:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-lwjgl3:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-binding-natives:$imguiVersion")) {}

    minecraftRuntimeLibraries("com.akuleshov7:ktoml-core-jvm:0.5.1")
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-app:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-binding:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-lwjgl3:$imguiVersion")) {}
    minecraftRuntimeLibraries(compileOnly("team.0mods:imgui-binding-natives:$imguiVersion")) {}
    minecraftRuntimeLibraries("effekseer.swig:Swig:1.0")
    minecraftRuntimeLibraries("com.tianscar.imageio:imageio-apng:1.0.1")
    minecraftRuntimeLibraries("org.joml:joml:1.10.8")

    implementation("io.github.classgraph:classgraph:4.8.173")

    implementation("ru.hollowhorizon:HollowCore-fabric-1.20.1:2.0.12:dev")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand("version" to project.version,
            "minecraft_version" to project.property("minecraft_version"),
            "loader_version" to project.property("loader_version"),
            "kotlin_loader_version" to project.property("kotlin_loader_version"))
    }
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}
