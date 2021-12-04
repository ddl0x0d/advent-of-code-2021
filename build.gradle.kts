plugins {
    application
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("com.willowtreeapps.assertk:assertk:0.25")
}

val applicationClass = "aoc2021.ApplicationKt"

application {
    mainClass.set(applicationClass)
}

tasks {
    jar {
        from(
            configurations.runtimeClasspath.get()
                .map { if (it.isDirectory) it else zipTree(it) }
                + sourceSets.main.get().output
        )
        archiveBaseName.set("aoc2021")
        manifest {
            attributes("Main-Class" to applicationClass)
        }
    }
    test {
        useJUnitPlatform()
    }
}
