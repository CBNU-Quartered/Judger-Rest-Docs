val snippetsDir by extra { file("build/generated-snippets") }

plugins {
    kotlin("plugin.spring")
    kotlin("jvm")

    id("org.springframework.boot")

    val asciiDocVersion = "1.5.3"
    id("org.asciidoctor.convert") version asciiDocVersion
}

dependencies {
    implementation(project(":judger-api"))

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit")
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")


    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.1.RELEASE")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.mockito:mockito-core:2.22.0")
}

tasks {
    //    val testTask = withType<Test> {
//        outputs.dir(snippetsDir)
//    }
//
//    withType<AsciidoctorTask> {
//        dependsOn(testTask)
//        inputs.dir(snippetsDir)
//        sources(delegateClosureOf<PatternSet> {
//            include("$projectDir/src/docs/index.adoc")
//        })
//        attributes.put("snippets", snippetsDir)
//        doLast {
//            copy {
//                from("$buildDir/asciidoc/html5")
//                into("$projectDir/src/main/resources/static")
//            }
//        }
//    }
    test {
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn("test")
    }

    task("copyDocument") {
        dependsOn(asciidoctor)

        copy{
            from("$buildDir/asciidoc/html5/")
            into("$projectDir/src/main/resources/static/docs")
        }
    }

    build {
        dependsOn("copyDocument")
    }




    withType<Test> {
        useJUnitPlatform()
    }
}