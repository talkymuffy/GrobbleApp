plugins {
    id("java")
    application
}

group = "com.smcc"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
application {
    mainClass = "com.smcc.Main"
}
application {
    applicationDefaultJvmArgs = listOf("-Dgreeting.language=en")
}