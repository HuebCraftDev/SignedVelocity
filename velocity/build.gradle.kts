plugins {
    alias(libs.plugins.runvelocity)
    alias(libs.plugins.idea.ext)
    alias(libs.plugins.blossom)
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.velocity.api)
    compileOnly(libs.velocity.proxy)
    annotationProcessor(libs.velocity.api)
    implementation(libs.bstats)

    compileOnly(libs.packetevents)
    compileOnly(libs.vpacketevents)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    clean {
        // Deletes the directory that is generated by the runVelocity plugin
        delete("run")
    }
    runVelocity {
        velocityVersion(libs.versions.velocity.get())
    }
    shadowJar {
        archiveBaseName.set("${rootProject.name}-Proxy")
        archiveClassifier.set("")
        relocate("org.bstats", "io.github._4drian3d.signedvelocity.libs.bstats")
        minimize()
        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
            }
        }
    }
}
