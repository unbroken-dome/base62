plugins {
    `java-library`
    groovy
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}


repositories {
    mavenCentral()
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    withSourcesJar()
    withJavadocJar()
}


dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    testImplementation("org.spockframework:spock-core:1.3-groovy-2.5")
}


publishing {
    publications {
        create("mavenJava", MavenPublication::class) {
            from(components["java"])
            pom {
                val githubRepo = providers.gradleProperty("githubRepo")
                val githubUrl = githubRepo.map { "https://github.com/$it" }

                name.set(providers.gradleProperty("projectName"))
                description.set(providers.gradleProperty("projectDescription"))
                url.set(providers.gradleProperty("projectUrl"))
                licenses {
                    license {
                        name.set(providers.gradleProperty("projectLicenseName"))
                        url.set(providers.gradleProperty("projectLicenseUrl"))
                    }
                }
                developers {
                    developer {
                        name.set(providers.gradleProperty("developerName"))
                        email.set(providers.gradleProperty("developerEmail"))
                        url.set(providers.gradleProperty("developerUrl"))
                    }
                }
                scm {
                    url.set(githubUrl.map { "$it/tree/master" })
                    connection.set(githubRepo.map { "scm:git:git://github.com/$it.git" })
                    developerConnection.set(githubRepo.map { "scm:git:ssh://github.com:$it.git" })
                }
                issueManagement {
                    url.set(githubUrl.map { "$it/issues" })
                    system.set("GitHub")
                }
            }
        }
    }

    repositories {
        maven {
            name = "local"
            url = uri("$buildDir/repos/releases")
        }
    }
}


signing {
    sign(publishing.publications["mavenJava"])
}


nexusPublishing {
    repositories {
        sonatype()
    }
}
