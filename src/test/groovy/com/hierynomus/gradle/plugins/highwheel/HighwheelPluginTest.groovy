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

import nebula.test.IntegrationSpec
import org.gradle.testkit.runner.GradleRunner

class HighwheelPluginTest extends IntegrationSpec {

  def "should add highwheel task"() {
    given:
    writeFile(buildFile, """
apply plugin: "java"
apply plugin: "com.github.hierynomus.highwheel"
//
//model {
//  highwheel {
//    sourceSets.add(project.sourceSets.main)
//  }
//}
""")

    when:
    def result = runTasksSuccessfully("build")

    then:
    result.standardOutput.contains("Running Highwheel analysis...")
  }

  private void writeFile(File destination, String content) throws IOException {
    destination.withWriter { w ->
      w.write(content)
    }
  }

}
