plugins {
    id("java")
}

group = "fr.euphyllia.skyllia_papi"
version = "1.0.2-dev"

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
    compileOnly(files("./libs/Skyllia-1.0-73-all.jar"))
    compileOnly(files("./libs/SkylliaOre-1.0.2.jar"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}