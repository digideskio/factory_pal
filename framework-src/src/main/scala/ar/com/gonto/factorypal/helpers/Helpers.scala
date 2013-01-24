package ar.com.gonto.factorypal
package helpers

import scala.util.Try
import scala.reflect.runtime.universe.TypeTag

/**
* By defining an object as a subtype of this abstract class, using FactoryPal
* becomes much easier. This class provides convenient access to the register method
* defined on the FactoryPal object and requires a register method to be implemented.

* Example:
*  case class Milk(fatPerc: Int)
*
*  object MilkO extends PalObject[Milk] {
*   def register() = {
*     FactoryPal.register[Milk]() { m =>
*       m.fatPerc.mapsTo(2)
*     }
*   }
* }
*
* An object for testing can now be created simply be calling MilkO().
*
*/
abstract class PalObject[T](implicit m: Manifest[T]) {

  def create(symbol: Option[Symbol] = None) = Try(FactoryPal.create[T](symbol)())

  def apply(symbol: Option[Symbol] = None) = FactoryPal.create[T](symbol)()

}

trait PalTrait {
  def register(): Unit
}

trait SpecHelper {
  def register[T: TypeTag]() {
    val d = Scanner.sealedDescendants[T]
    d map { x: reflect.runtime.universe.Symbol => Class.forName(x.fullName).newInstance.asInstanceOf[PalTrait] } foreach {_.register()}
  }
}
