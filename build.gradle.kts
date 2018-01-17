val kotlinVersion = "1.2.10"


plugins {
    idea
    kotlin("jvm") version "1.2.10"
    kotlin("plugin.spring") version "1.2.10"
    id("cn.bestwu.kotlin-publish") version "0.0.15"
    id("org.springframework.boot") version "1.5.9.RELEASE"
}

group = "cn.bestwu"
version = "0.0.1"

idea {
    module {
        outputDir = java.sourceSets["main"].java.outputDir
        testOutputDir = java.sourceSets["test"].java.outputDir
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compile(kotlin("reflect", kotlinVersion))

    compile("org.springframework.boot:spring-boot")
    compile("cn.bestwu:fastdfs-client-java:1.27")

    compileOnly("org.springframework.boot:spring-boot-configuration-processor")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("commons-logging:commons-logging")
    testCompile(kotlin("test-junit", kotlinVersion))
}


tasks {
    "compileJava" {
        dependsOn("processResources")
    }
}