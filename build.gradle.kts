plugins {
    id("java-library")
    id("maven-publish")
}

group = "net.darkness"
version = "1.5"

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        register("release", MavenPublication::class) {
            from(components["java"])
            artifactId = "java-payok-sdk"
        }
    }
}

repositories {
    mavenCentral()
    maven(url = "https://www.jitpack.io")
}

dependencies {
    implementation("com.sparkjava:spark-core:2.9.4")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.jar {
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "net.darkness.Payok"
    }
}
