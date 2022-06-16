import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("multiplatform") version "1.7.0"
    id("org.jetbrains.dokka") version "1.6.21"
    id("maven-publish")
    signing
    id("nebula.release") version "16.0.0"
}

allprojects {
    apply { plugin("maven-publish") }
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
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                api("io.kotest:kotest-assertions-core:5.3.1")
            }
        }
        val commonTest by getting
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                api(kotlin("test-junit5"))
                api(project.dependencies.platform("org.junit:junit-bom:5.9.0-M1"))
                listOf("api", "engine").forEach { api("org.junit.jupiter:junit-jupiter-$it") }
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

tasks {
    withType<Test>().configureEach {
        testLogging {
            events = setOf(
                TestLogEvent.SKIPPED,
                TestLogEvent.FAILED,
                TestLogEvent.STANDARD_OUT,
                TestLogEvent.STANDARD_ERROR
            )
            exceptionFormat = TestExceptionFormat.FULL
            showExceptions = true
            showCauses = true
            showStackTraces = true
        }
    }
}

val dokkaOutputDir = buildDir.resolve("dokka")
tasks.dokkaHtml.configure {
    outputDirectory.set(dokkaOutputDir)
    dokkaSourceSets {
        configureEach {
            displayName.set(
                when (platform.get()) {
                    org.jetbrains.dokka.Platform.jvm -> "jvm"
                    org.jetbrains.dokka.Platform.js -> "js"
                    org.jetbrains.dokka.Platform.native -> "native"
                    org.jetbrains.dokka.Platform.common -> "common"
                }
            )
        }
    }
}
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(deleteDokkaOutputDir) // TODO add jsGenerateExternalsIntegrated
    from(tasks.dokkaHtml.map { it.outputs })
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
            url = uri("https://maven.pkg.github.com/bkahlert/kommons")
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
val signingTasks = tasks.filter { it.name.startsWith("sign") }
listOf(
    tasks.getByName("publishKotlinMultiplatformPublicationToMavenLocal"),
    tasks.getByName("publishJsPublicationToMavenLocal"),
    tasks.getByName("publishJvmPublicationToMavenLocal"),
).forEach {
    it.dependsOn(signingTasks)
}
