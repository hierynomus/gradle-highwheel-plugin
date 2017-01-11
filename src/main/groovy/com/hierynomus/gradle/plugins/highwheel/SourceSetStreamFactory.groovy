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

import org.gradle.api.tasks.SourceSet
import org.pitest.highwheel.report.FileStreamFactory

class SourceSetStreamFactory extends FileStreamFactory {
  private final SourceSet sourceSet
  private final Map<String, OutputStream> streams = new HashMap<String, OutputStream>();

  public SourceSetStreamFactory(final SourceSet sourceSet) {
    super(null)
    this.sourceSet = sourceSet
  }

  public OutputStream getStream(final String name) {
    OutputStream os = this.streams.get(name)
    if (os != null) {
      return os;
    }
    try {
      os = new FileOutputStream(this.sourceSet.allSource.filter({ f -> f.name == name }).getSingleFile());
      this.streams.put(name, os);
      return os;
    } catch (final IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public void close() {
    for (final OutputStream each : this.streams.values()) {
      try {
        each.close();
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

}
