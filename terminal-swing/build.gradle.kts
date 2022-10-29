plugins {
    id("java-common-conventions")
    application
}

dependencies {
    implementation(project(":terminal-backend"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClass.set("nl.ricoapon.terminal.swing.JTerminal")
}
