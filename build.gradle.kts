plugins {
    `java-library`
    groovy
    `maven-publish`
}


repositories {
    mavenCentral()
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    testImplementation("org.spockframework:spock-core:1.1-groovy-2.4")
}
