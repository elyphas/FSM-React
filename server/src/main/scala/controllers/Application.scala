package controllers

import java.nio.ByteBuffer

import boopickle.Default._
import javax.inject.Inject
import play.api.{Configuration, Environment}
import play.api.mvc._
import services.ApiService
import spatutorial.shared.Api

import scala.concurrent.ExecutionContext.Implicits.global

object Router extends autowire.Server[ByteBuffer, Pickler, Pickler] {
  override def read[R: Pickler](p: ByteBuffer) = Unpickle[R].fromBytes(p)
  override def write[R: Pickler](r: R) = Pickle.intoBytes(r)
}

class Application @Inject() ( components: ControllerComponents,
                              implicit val config: Configuration,
                              env: Environment,
                              protected val apiService: ApiService
                            ) extends AbstractController(components) {

  def index = Action(parse.json) { Ok(views.html.index("WebSicap")) }

  def validate = Action { implicit request =>
    Ok(views.html.index("Zyckap")) //withSession("sess-zyckap" -> "true")
  }

  def autowireApi(path: String) = Action.async(parse.raw) {
    implicit request =>
      println(s"Request path: $path")

      // get the request body as ByteString
      val b = request.body.asBytes(parse.UNLIMITED).get

      // call Autowire route
      Router.route[Api](apiService)(
        autowire.Core.Request(path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes(b.asByteBuffer))
      ).map(buffer => {
        val data = Array.ofDim[Byte](buffer.remaining())
        buffer.get(data)
        Ok(data)
      })
  }

  def logging = Action(parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach { msg =>
        println(s"CLIENT - $msg")
      }
      Ok("")
  }

}
