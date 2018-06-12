package com.ghedeon.gradle

import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.Plugin
import org.gradle.api.Project

class Utils implements Plugin<Project> {
    void apply(Project project) {
        BuildType.metaClass.strField =
                { key, value -> delegate.buildConfigField "String", key, "\"$value\"" }
    }
}
