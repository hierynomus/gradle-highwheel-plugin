/*
 *    Copyright 2016 Jeroen van Erp <jeroen@hierynomus.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.hierynomus.gradle.plugins.highwheel

import org.codehaus.groovy.util.StringUtil
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.model.*

@Managed
interface HighwheelConfig {
  void setFilter(String filter)

  String getFilter()

//  LanguageSourceSet getSourceSet()
//
//  void setSourceSet(LanguageSourceSet sourceSets)
}

public class HighwheelPlugin implements Plugin<Project> {

//  @Model
//  void highwheel(HighwheelConfig highwheel) {}
//
//  @Mutate
//  void createHighwheelTask(@Path("rootProject") Project rootProject, HighwheelConfig highwheel) {
//    println("Creating highwheel${rootProject.name.capitalize()}")
//    rootProject.tasks.create("highwheel${rootProject.name.capitalize()}", HighwheelTask.class) { t ->
//    }
//  }

  @Override
  void apply(final Project project) {
    def rootProject = project.rootProject

    println("Creating highwheel${rootProject.name.capitalize()}")
    def highwheelTask = rootProject.tasks.create("highwheel${rootProject.name.capitalize()}", HighwheelTask.class) { t ->
    }

    project.tasks.getByName("build").dependsOn highwheelTask

  }
}
