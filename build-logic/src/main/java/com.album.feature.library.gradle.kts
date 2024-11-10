import com.jungha.build_logic.applyAndroidLibrary
import com.jungha.build_logic.applyComposeLibrary
import com.jungha.build_logic.applyHiltLibrary

applyAndroidLibrary()
applyComposeLibrary()
applyHiltLibrary()

dependencies {
    "implementation"(project(":core:designsystem"))
    "implementation"(project(":core:domain"))
    "implementation"(project(":core:data"))
    "implementation"(project(":feature:base"))
}