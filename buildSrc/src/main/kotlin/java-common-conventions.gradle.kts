plugins {
    java
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_17

// Enable preview for using pattern matching with switch.
tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}
