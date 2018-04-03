package spatutorial.client

import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom
import spatutorial.client.components.GlobalStyles
import spatutorial.client.logger._
import spatutorial.client.modules._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import CssSettings._
import scalacss.ScalaCssReact._

@JSExportTopLevel("SPAMain")
object SPAMain extends js.JSApp {

  // Define the locations (pages) used in this application
  sealed trait Loc

  case object PartidaLoc extends Loc

  // configure the router
  val routerConfig = RouterConfigDsl[Loc].buildConfig { dsl =>
    import dsl._
    // wrap/connect components to the circuit
      (staticRoute(root, PartidaLoc) ~> renderR( ctl => frmPartida())
      | staticRoute("#partida", PartidaLoc) ~> renderR(ctl => frmPartida())
      ).notFound(redirectToPage(PartidaLoc)(Redirect.Replace))
  }.renderWith(layout)

  // base layout for all pages
  def layout(c: RouterCtl[Loc], r: Resolution[Loc]) = {
    <.div(^.className:="wrapper",
      <.header(
        <.h5("Web Sicap")),
      // here we use plain Bootstrap class names as these are specific to the top level layout defined here
      <.aside( ^.float:="left", ^.width:="150", ^.height:="600", ^.backgroundColor := " #ebf4f5 ",
        ^.border := "1px solid",
        MainMenu(c, r.page) ),
      // currently active module is shown in this container
      <.main(r.render())
    )
  }

  @JSExport
  def main(): Unit = {
    log.warn("Application starting")
    // send log messages also to the server
    log.enableServerLogging("/logging")
    log.info("This message goes to server as well")

    // create stylesheet
    GlobalStyles.addToDocument()
    // create the router
    val router = Router(BaseUrl.until_#, routerConfig)
    // tell React to render the router in the document body
    router().renderIntoDOM(dom.document.getElementById("root"))
  }
}
