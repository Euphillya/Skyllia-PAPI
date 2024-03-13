plugins {
    id("java")
}

group = "fr.euphyllia.skyllia_papi"
version = "1.0-SNAPSHOT"

val papiRepo = "https://repo.extendedclip.com/content/repositories/placeholderapi/";
val paperMC = "https://repo.papermc.io/repository/maven-public/";
val skylliaRepo = "https://maven.pkg.github.com/Euphillya/Skyllia";

repositories {
    mavenCentral()
    maven(papiRepo)
    maven(paperMC)
    maven{
        url = uri(skylliaRepo)
        credentials {
            username = System.getenv("GITHUB_USERNAME") ?: ""
            password = System.getenv("GITHUB_TOKEN") ?: ""
        }
    }
}

dependencies {
    compileOnly("me.clip:placeholderapi:2.11.5")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("fr.euphyllia.skyllia:api:1.0-RC5.4")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}