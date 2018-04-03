package spatutorial.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
//import japgolly.univeq.UnivEq

import scala.language.implicitConversions
import scala.scalajs.js
import scalacss.ScalaCssReact._
import spatutorial.client.CssSettings._

/**
 * Common Bootstrap components for scalajs-react
 */
object Bootstrap {

  // shorthand for styles
  @inline private def bss = GlobalStyles.bootstrapStyles

  @js.native
  trait BootstrapJQuery extends JQuery {
    def modal(action: String): BootstrapJQuery = js.native
    def modal(options: js.Any): BootstrapJQuery = js.native
  }

  implicit def jq2bootstrap(jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]

  // Common Bootstrap contextual styles
  object CommonStyle extends Enumeration {
    val default, primary, success, info, warning, danger = Value
  }

  /*object Button {

    case class Props(onClick: Callback, style: CommonStyle.Value = CommonStyle.default, addStyles: Seq[StyleA] = Seq())

    val component = ScalaComponent.builder[Props]("Button")
      .renderPC((_, p, c) =>
        <.button(bss.buttonOpt(p.style), p.addStyles.toTagMod, ^.tpe := "button", ^.onClick --> p.onClick, c)
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }*/


  object Button {

    case class Props(onClick: Callback, style: CommonStyle.Value = CommonStyle.default,
                     addStyles: Seq[StyleA] = Seq(),
                     otrosStyles: Seq[TagMod]=Seq()
                    )

    val component = ScalaComponent.builder[Props]("Button")
      .renderPC((_, p, c) =>
        <.button(bss.buttonOpt(p.style), p.addStyles.toTagMod, p.otrosStyles.toTagMod, ^.tpe := "button", ^.onClick --> p.onClick, c)
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  object CheckBox {
    case class Props( idx:String="", label: String = "", onClick: Callback, value: Boolean = false,
                      style: CommonStyle.Value = CommonStyle.default,
                      addStyles: Seq[StyleA] = Seq(), otrosStyles: Seq[TagMod]=Seq() )

    val component = ScalaComponent.builder[Props]("Checkbox")
      .renderPC((_, p, c) =>
        <.div(^.float := "left",
          <.label(p.label, ^.`for` := p.idx, ^.fontWeight:="normal", ^.display := "block", ^.marginTop := "10"),
          <.input.checkbox(bss.buttonOpt(p.style), p.addStyles.toTagMod,
            p.otrosStyles.toTagMod, ^.paddingRight := "50", ^.onClick --> p.onClick, ^.checked := p.value)
          //<.input( ^.tpe:="checkbox", bss.buttonOpt(p.style), p.addStyles, ^.onClick --> p.onClick, c )
        )
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }

  /*object Panel {

    case class Props(heading: String, style: CommonStyle.Value = CommonStyle.default)

    val component = ScalaComponent.builder[Props]("Panel")
      .renderPC((_, p, c) =>
        <.div(bss.panelOpt(p.style),
          <.div(bss.panelHeading, p.heading),
          <.div(bss.panelBody, c)
        )
      ).build

    def apply(props: Props, children: VdomNode*) = component(props)(children: _*)
    def apply() = component
  }*/

  /*object Modal {

    case class Props(header: Callback => VdomNode, footer: Callback => VdomNode, closed: Callback,
                     backdrop: Boolean = true, keyboard: Boolean = true)

    class Backend(t: BackendScope[Props, Unit]) {
      def hide =
        t.getDOMNode.map(jQuery(_).modal("hide")).void

      def hidden(e: JQueryEventObject): js.Any = {
        t.props.flatMap(_.closed).runNow()
      }

      def render(p: Props, c: PropsChildren) = {
        val modalStyle = bss.modal
        <.div(modalStyle.modal, modalStyle.fade, ^.role := "dialog", ^.aria.hidden := true,
            <.div(modalStyle.dialog,
              <.div(modalStyle.content,
                  <.div(modalStyle.header, p.header(hide)),
                  <.div(modalStyle.body, c),
                  <.div(modalStyle.footer, p.footer(hide))
              )
            )
        )
      }
    }

    val component = ScalaComponent.builder[Props]("Modal")
      .renderBackendWithChildren[Backend]
      .componentDidMount(scope => Callback {
        val p = scope.props
        jQuery(scope.getDOMNode).modal(js.Dynamic.literal("backdrop" -> p.backdrop, "keyboard" -> p.keyboard, "show" -> true))
        jQuery(scope.getDOMNode).on("hidden.bs.modal", null, null, scope.backend.hidden _)
      })
      .build

    def apply(props: Props, children: VdomElement*) = component(props)(children: _*)
    def apply() = component
  }*/

}
