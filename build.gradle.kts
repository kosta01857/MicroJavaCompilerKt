plugins {
    kotlin("jvm") version "1.9.21"
    application
}

val javaDestDir = "src/main/java/com/kosta/pp1"

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation(fileTree("lib"){include("*.jar")})
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.register<Delete>("cleanAll"){
    description = "deletes files generated by JFlex and CUP"
    group = "cleanup"

    delete(
        file("$javaDestDir/ast"),
        file("$javaDestDir/sym.java"),
        file("$javaDestDir/MJParser.java"),
    )

}


tasks.register<Exec>("generateParser") {
    dependsOn("cleanAll")
    description = "Generates parser using CUP."
    group = "code generation"

    // Command to execute
    commandLine("java", "-jar", "lib/cup_v10k.jar",
        "-destdir", javaDestDir,
        "-ast", "$javaDestDir.ast",
        "-parser", "MJParser",
        "-buildtree",
        "spec/mjparser.cup")
}

tasks.register("repackageParser"){
    description = "fixes package names due to a known bug."
    group = "code generation"
    dependsOn("generateParser")
    val sourceDir = file("$javaDestDir/ast")
    inputs.dir(sourceDir)
    doLast {
        val token = "src/main/java/com/kosta/pp1.ast"
        val replacement = "com.kosta.pp1.ast"
        sourceDir.walkTopDown().filter { it.isFile }.forEach {
                file ->
            logger.info("replacing")
            val content = file.readText()
            val updatedContent = content.replace(token,replacement)
            file.writeText(updatedContent)
        }
    }
}



tasks.register<Exec>("generateLexer"){
    description = "Generates lexer using JFlex."
    group = "code generation"
    dependsOn("repackageParser")
    commandLine("java", "-jar", "lib/JFlex.jar",
        "-d", javaDestDir,
        "./spec/mjlexer.lex")
}

application{
    mainClass.set("com.kosta.pp1.CompilerKt")
}