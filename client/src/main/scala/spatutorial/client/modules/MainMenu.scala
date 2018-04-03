package spatutorial.client.modules

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.html_<^._
import spatutorial.client.SPAMain._
import spatutorial.client.components.Icon._
import spatutorial.client.components._

import spatutorial.client.services._

import scalacss.ScalaCssReact._

object MainMenu {
  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props(router: RouterCtl[Loc], currentLoc: Loc)

  case class State( )

  private case class MenuItem(idx: Int, label: (Props) => VdomNode, icon: Icon, location: Loc, submenu: String)

  private class Backend($: BackendScope[Props, State]) {

    def menu(props: Props, s: State) = {
      <.li(^.key := 1, (^.className := "active").when(props.currentLoc == PartidaLoc),
        props.router.link(PartidaLoc)(Icon.check, " ", "Partida")
      )
    }

    def render(props: Props, s: State) = {
      <.ul(bss.aside)(
        menu(props, s)
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("MainMenu")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply(ctl: RouterCtl[Loc], currentLoc: Loc): VdomElement =
      component(Props(ctl, currentLoc))
}
