@file:Suppress("UNUSED_VARIABLE")

import org.jetbrains.kotlin.gradle.targets.js.yarn.yarn

plugins {
    kotlin("multiplatform") version "1.7.10"
    id("org.jetbrains.dokka") version "1.7.10"
    id("maven-publish")
    signing
    id("nebula.release") version "16.0.0"
}

description = "Kommons Test is a Kotlin Multiplatform Library to ease testing"
group = "com.bkahlert.kommons"

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        nodejs()
        yarn.ignoreScripts = false // suppress "warning Ignored scripts due to flag." warning
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                implementation("io.kotest:kotest-common:5.4.1")
                api("io.kotest:kotest-assertions-core:5.4.1")
            }
        }
        val commonTest by getting
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                api(kotlin("test-junit5"))
                api(project.dependencies.platform("org.junit:junit-bom:5.9.0"))
                listOf("api", "engine").forEach { api("org.junit.jupiter:junit-jupiter-$it") }
                listOf("commons", "launcher").forEach { implementation("org.junit.platform:junit-platform-$it") }
            }
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting

        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlin.time.ExperimentalTime")
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlin.experimental.ExperimentalTypeInference")
                progressiveMode = true // false by default
            }
        }
        jvmMain.languageSettings.apply {
            optIn("kotlin.reflect.jvm.ExperimentalReflectionOnLambdas")
        }
    }
}

val javadocJar by tasks.registering(Jar::class) {
    description = "Generates a JavaDoc JAR using Dokka"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    archiveClassifier.set("javadoc")
    tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml").also {
        dependsOn(it)
        from(it.get().outputDirectory)
    }
}

publishing {
    repositories {
        @Suppress("SpellCheckingInspection")
        maven {
            name = "OSSRH"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }

        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bkahlert/kommons-test")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        withType<MavenPublication>().configureEach {
            artifact(javadocJar)
            pom {
                name.set("Kommons")
                description.set(project.description)
                url.set("https://github.com/bkahlert/kommons-test")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/bkahlert/kommons-test/blob/master/LICENSE")
                    }
                }
                scm {
                    url.set("https://github.com/bkahlert/kommons-test")
                    connection.set("scm:git:https://github.com/bkahlert/kommons-test.git")
                    developerConnection.set("scm:git:https://github.com/bkahlert/kommons-test.git")
                }
                issueManagement {
                    url.set("https://github.com/bkahlert/kommons-test/issues")
                    system.set("GitHub")
                }
                ciManagement {
                    url.set("https://github.com/bkahlert/kommons-test/issues")
                    system.set("GitHub")
                }
                developers {
                    developer {
                        id.set("bkahlert")
                        name.set("Björn Kahlert")
                        email.set("mail@bkahlert.com")
                        url.set("https://bkahlert.com")
                        timezone.set("Europe/Berlin")
                    }
                }
            }
        }
    }
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

// getting rid of missing dependency declarations
tasks.filter { it.name.startsWith("sign") }.also { signingTasks ->
    tasks.filter { it.name.startsWith("publish") && it.name.contains("Publication") }.forEach { it.dependsOn(signingTasks) }
}
