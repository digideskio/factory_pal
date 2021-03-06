/*
 * *
 *  * Copyright 2012 Martin Gontovnikas (martin at gonto dot com dot ar) - twitter: @mgonto
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package ar.com.gonto.factorypal.reflection

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ar.com.gonto.factorypal.{NotAllConstructed, Age, Name, Person}
import ar.com.gonto.factorypal.fields.{FieldSetter, SpecifiedFieldSetter}

/**
 * TODO: Add a comment
 * @author mgonto
 * Created Date: 12/13/12
 */
class ObjectReflectorSpec extends FunSpec with ShouldMatchers {

  describe("The ObjectReflector") {
    it("should instantiate the Name class correctly") {
      val name = "gonto"
      val nameSetter = new SpecifiedFieldSetter[Name, String]("name", name, name.getClass)
      val person = ObjectReflector.create(List(nameSetter))
      person.name should equal(name)
    }

    it("should instantiate the age class correctly") {
      val age = 23
      val ageSetter = new SpecifiedFieldSetter[Age, Int]("age", age, age.getClass)
      val list =  List(ageSetter)
      val person = ObjectReflector.create(list)
      person.age should equal(age)
    }

    it("should set all of the fields not only those in constructor")  {
      val name = "gonto"
      val fill = "ejem"
      val nameSetter = new SpecifiedFieldSetter[NotAllConstructed, String]("name", name, name.getClass)
      val fillSetter = new SpecifiedFieldSetter[NotAllConstructed, String]("fill",fill, fill.getClass)
      val list : List[FieldSetter[NotAllConstructed, Any]] =  List(fillSetter, nameSetter)
      val created = ObjectReflector.create(list)
      created.name should equal(name)
      created.fill should equal(fill)

    }
  }
}
