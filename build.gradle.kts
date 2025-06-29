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
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.5")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.4")
    implementation("edu.stanford.nlp:stanford-corenlp:4.5.4:models")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("io.github.fastily:jwiki:1.11.0")


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