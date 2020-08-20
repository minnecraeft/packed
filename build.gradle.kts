plugins {
    id("fabric-loom") version "0.5.1"
    id("maven-publish")
    id("com.matthewprenger.cursegradle") version "1.4.0"
}

val minecraftVersion = findProperty("minecraft_version")
val yarnMappings = findProperty("yarn_mappings")
val fabricVersionLoader = findProperty("loader_version")
val fabricVersionApi = findProperty("fabric_version")

dependencies {
    //to change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${fabricVersionLoader}")

    // Fabric API. This is technically optional, but you probably want it anyway.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${fabricVersionApi}")

    // PSA: Some older mods, compiled on Loom 0.2.1, might have outdated Maven POMs.
    // You may need to force-disable transitiveness on them.
    implementation("org.jetbrains:annotations:16.0.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}

minecraft {
    accessWidener("src/main/resources/packed.accesswidener")
}

configure<BasePluginConvention> {
    archivesBaseName = "$archivesBaseName-mc${minecraftVersion}"
}

/*
 * ensure that the encoding is set to UTF-8, no matter what the system default is
 * this fixes some edge cases with special characters not displaying correctly
 */
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

/*
 *  java 8 turns on doc lint which we fail
 */
tasks.withType<Javadoc>().configureEach {
    (options as CoreJavadocOptions).addStringOption("Xdoclint:none", "-quiet")
}

tasks.named<Copy>("processResources") {
    from(sourceSets["main"].resources.srcDirs) {
        include("fabric.mod.json")
        expand(
                "version" to project.version,
                "minecraft_version" to minecraftVersion
        )
    }
    from(sourceSets["main"].resources.srcDirs) {
        exclude("fabric.mod.json")
    }
}

tasks.named<Jar>("jar") {
    from("LICENSE", "CHANGELOG.md")
}

tasks.named("assemble").configure {
    dependsOn("sourcesJar")
    dependsOn("javadocJar")
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks["sourcesJar"]) {
                builtBy(tasks["remapSourcesJar"])
            }
            artifact(tasks["remapJar"]) {
                builtBy(tasks["remapJar"])
            }
        }
    }
}

/*curseforge{
    apiKey = project.hasProperty("curse_api_key") ? project.curse_api_key : "###"
    project {
        id = '403096'
        changelogType = 'markdown'
        changelog = file("changelog.md")
        releaseType = 'release'
        addGameVersion project.minecraft_version
        addGameVersion "Java 8"
        addGameVersion "Fabric"

        mainArtifact(remapJar) {
            displayName = "Packed $project.version for $project.minecraft_version"

            relations {
                requiredDependency('fabric-api')
            }
        }

        afterEvaluate {
            uploadTask.dependsOn(remapJar)
        }
    }

    options {
        forgeGradleIntegration = false
    }
}*/
