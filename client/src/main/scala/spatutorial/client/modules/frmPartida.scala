package spatutorial.client.modules
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import akka.actor._
import akka.actor

import spatutorial.client.components.Bootstrap.{Button}
import spatutorial.client.components._

sealed trait FSMState
case object FSMInit extends FSMState

object frmPartida {

  @inline private def bss = GlobalStyles.bootstrapStyles

  val system = ActorSystem.create("ActorSystemFSM")

  sealed trait ReactState
  case object Initial extends ReactState

  class Backend(bs: BackendScope[Unit, ReactState]) {

    class FSMBackend() extends FSM[FSMState, ReactState] {

      println("123")

      startWith( FSMInit, Initial )

      when(FSMInit) {
        case Event(_, _) => {
          println("mensage Start!!!!!!!!!!!!!")
          stay
        }
      }
      whenUnhandled {
        case Event(_, _) => {
          println("Start*****")
          stay
        }
      }
      initialize()
    }

    val fsm = system.actorOf( actor.Props(new FSMBackend()), name = "FSM")

    def onStart = Callback {
      fsm ! "probando"
      println("mmmm")
    }

    def	render(reactState: ReactState) = <.div("Imprimir", Button(Button.Props(onStart, addStyles = Seq( bss.buttonXS)), "Buscar"))

  }

  val component = ScalaComponent.builder[Unit]("frmPartida")
    .initialState[ReactState](Initial)
    .renderBackend[Backend]
    .build

  def apply() = component()
}