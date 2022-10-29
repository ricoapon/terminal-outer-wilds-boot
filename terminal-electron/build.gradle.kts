plugins {
    id("com.github.node-gradle.node") version "3.5.0"
}

tasks.register<Copy>("build") {
    dependsOn("npmInstall")
    dependsOn(":terminal-spring-boot:bootJar")
    from("${projectDir}/../terminal-spring-boot/build/libs/terminal-spring-boot.jar")
    into("${projectDir}/app")
}

tasks.register<com.github.gradle.node.npm.task.NpxTask>("runElectron") {
    dependsOn("build")
    command.set("electron")
    args.set(listOf("main.js"))
}

tasks.register("clean") {
    delete("${projectDir}/app")
    delete("${projectDir}/node_modules")
}
