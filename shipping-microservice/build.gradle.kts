import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("plugin.allopen") version "1.3.61"
    kotlin("plugin.jpa") version "1.3.61"
    kotlin("kapt") version "1.3.61"
}

group = "com.oriaxx77.microserviceplay"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_13

repositories {
    mavenCentral()
    jcenter()
    maven( url ="https://dl.bintray.com/arrow-kt/arrow-kt/" )
}

val arrow_version = "0.10.4"

dependencies {

    implementation(project(":server-commons"))

    implementation("io.arrow-kt:arrow-fx:$arrow_version")
    implementation("io.arrow-kt:arrow-optics:$arrow_version")
    implementation("io.arrow-kt:arrow-syntax:$arrow_version")
    kapt("io.arrow-kt:arrow-meta:$arrow_version")

    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation( "org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation( "org.springframework.security:spring-security-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("com.ninja-squad:springmockk:1.1.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
    annotation("javax.persistence.MappedSuperclass")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Copy>("copyFatJar") {
    from("$buildDir/libs/payment-microservice-0.0.1-SNAPSHOT.jar")
    into("${projectDir}/../infrastructure/kubernetes-docker-compose")
    dependsOn(tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar>())
}
