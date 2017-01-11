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

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.pitest.highwheel.Highwheel
import org.pitest.highwheel.bytecodeparser.ClassPathParser
import org.pitest.highwheel.bytecodeparser.classpath.CompoundClassPathRoot
import org.pitest.highwheel.bytecodeparser.classpath.DirectoryClassPathRoot
import org.pitest.highwheel.classpath.ClasspathRoot
import org.pitest.highwheel.cycles.Filter
import org.pitest.highwheel.model.ElementName
import org.pitest.highwheel.oracle.DependendencyStatus
import org.pitest.highwheel.oracle.FixedScorer
import org.pitest.highwheel.report.FileStreamFactory

public class HighwheelTask extends DefaultTask {
  String filter

  @TaskAction
  def run() {
    def parser = new ClassPathParser(new Filter() {
      @Override
      boolean include(final ElementName item) {
        return true
      }
    })
    def reportDir = new File(project.buildDir, "reports/highwheel")
    reportDir.mkdirs()

    def highwheel = new Highwheel(parser, new FixedScorer(DependendencyStatus.UNKNOWN), new FileStreamFactory(reportDir))
    logger.lifecycle("Running Highwheel analysis...")
    highwheel.analyse(makeRoot(project, this.&projectToClassesDir), makeRoot(project, this.&projectToTestDir))
  }

  def makeRoot(Project rootProject, Closure<ClasspathRoot> c) {
    new CompoundClassPathRoot(rootProject.allprojects.collect { c.call(it) })
  }


  def projectToTestDir(Project p) {
    new DirectoryClassPathRoot(p.sourceSets.test.output.classesDir)
  }

  def projectToClassesDir(Project p) {
    new DirectoryClassPathRoot(p.sourceSets.main.output.classesDir)
  }
}
