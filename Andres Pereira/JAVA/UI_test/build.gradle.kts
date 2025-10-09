plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    // Selenium 4
    testImplementation("org.seleniumhq.selenium:selenium-java:4.22.0")
    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

// Helpers para leer -D con defaults
val propBrowser     = providers.systemProperty("BROWSER").orElse("chrome")
val propHeadless    = providers.systemProperty("HEADLESS").orElse("false")
val propRemote      = providers.systemProperty("REMOTE").orElse("false")
val propGridUrl     = providers.systemProperty("GRID_URL").orElse("http://localhost:4444")
val propMobileDev   = providers.systemProperty("MOBILE_DEVICE").orElse("")

tasks.test {
    useJUnitPlatform()

    // 👉 Propagar -D al JVM de tests
    systemProperty("BROWSER",       propBrowser.get())
    systemProperty("HEADLESS",      propHeadless.get())
    systemProperty("REMOTE",        propRemote.get())
    systemProperty("GRID_URL",      propGridUrl.get())
    systemProperty("MOBILE_DEVICE", propMobileDev.get())

    // Hacer que cambiar estas props invalide cache
    inputs.property("browser",     propBrowser)
    inputs.property("headless",    propHeadless)
    inputs.property("remote",      propRemote)
    inputs.property("gridUrl",     propGridUrl)
    inputs.property("mobileDev",   propMobileDev)

    testLogging { events("passed", "skipped", "failed") }
    reports {
        junitXml.required.set(true)
        html.required.set(true)
    }
}

// --- Plantilla para crear tareas por navegador con reportes aislados
fun Test.configureReportsAndCommon(nameTag: String) {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }

    // Carpeta de reportes por navegador
    reports {
        junitXml.required.set(true)
        html.required.set(true)
        junitXml.outputLocation.set(layout.buildDirectory.dir("test-results/$nameTag"))
        html.outputLocation.set(layout.buildDirectory.dir("reports/tests/$nameTag"))
    }
    // También separo los binarios para evitar conflictos
    binaryResultsDirectory.set(layout.buildDirectory.dir("test-results/binary/$nameTag"))
}

// Chrome
tasks.register<Test>("testChrome") {
    configureReportsAndCommon("chrome")
    systemProperty("BROWSER", "chrome")
    systemProperty("HEADLESS", "true")   // cámbialo a false si quieres ver la UI
}

// Firefox
tasks.register<Test>("testFirefox") {
    configureReportsAndCommon("firefox")
    systemProperty("BROWSER", "firefox")
    systemProperty("HEADLESS", "true")
}

// Edge (Chromium)
tasks.register<Test>("testEdge") {
    configureReportsAndCommon("edge")
    systemProperty("BROWSER", "edge")
    systemProperty("HEADLESS", "true")
}

tasks.register("testAllBrowsers") {
    dependsOn("testChrome", "testFirefox", "testEdge")
}

tasks.register<Test>("mobilePixel5") {
    useJUnitPlatform()
    systemProperty("BROWSER", "chrome")         // o "edge"
    systemProperty("MOBILE_DEVICE", "Pixel 5")  // lista de nombres soportados por Chrome
    systemProperty("HEADLESS", "false")         // visible para “ver” el layout móvil
    // Opcional: limitar a una clase o test:
    filter { includeTestsMatching("tests.LoginTests") } // toda la clase
    // filter { includeTestsMatching("tests.LoginTests.T01*") } // solo T01
}

tasks.register<Test>("mobileIphone12") {
    useJUnitPlatform()
    systemProperty("BROWSER", "chrome")
    systemProperty("MOBILE_DEVICE", "iPhone 12 Pro")
    systemProperty("HEADLESS", "false")
    filter { includeTestsMatching("tests.LoginTests") }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.test {
    systemProperty("file.encoding", "UTF-8")
    jvmArgs("-Dfile.encoding=UTF-8")
}

