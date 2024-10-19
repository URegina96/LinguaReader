import org.gradle.kotlin.dsl.provider.inClassPathMode

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
//        maven{
//            url =uri ("https://repo1.maven.org/maven2/com/tom-roush/pdfbox-android/2.0.27.0/")
//        }
    }
}

rootProject.name = "LinguaReader"
include(":app")
 