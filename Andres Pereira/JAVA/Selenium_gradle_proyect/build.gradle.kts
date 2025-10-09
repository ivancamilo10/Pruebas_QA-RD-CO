plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java
    implementation("org.seleniumhq.selenium:selenium-java:4.36.0")
}

tasks.test {
    useJUnitPlatform()
}