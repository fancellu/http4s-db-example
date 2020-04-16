package example

import java.util.concurrent.Executors

import MyMain.{Greeting, ctx}
import cats.effect._
import cats.implicits._
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.getquill.{H2JdbcContext, SnakeCase, UpperCase}
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import io.circe.generic.auto._
import io.getquill.monad.Effect
import org.http4s.server.ServiceErrorHandler

object MyMain extends IOApp {

  lazy val ctx = new H2JdbcContext(SnakeCase, "ctx")

  case class Greeting(message: String)

  private val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name => Ok(s"Hello, $name.")
  }

  private val greetService = HttpRoutes.of[IO] {
    case GET -> Root / "greet"  => Ok(Greeting("hello there").asJson)
  }

  //case class Video(id: Int, name: String)


  private val videos = HttpRoutes.of[IO] {
    case GET -> Root / "video" => {
      import ctx._
      val q=quote{
        query[Video]
      }

      val out1=ctx.run(q)
      Ok(out1.asJson)
    }
  }

  private val tags = HttpRoutes.of[IO] {
    case GET -> Root / "tag" => {
      import ctx._
      val q=quote{
        query[Tag]
      }

      val out1=ctx.run(q)
      Ok(out1.asJson)
    }
  }

//  private def error(req:Request[IO]) :ServiceErrorHandler[IO]=  ServiceErrorHandler{
//    //case th:Throwable=>
//    //th.printStackTrace()
//    IO(InternalServerError)
//  }

  private val videotags = HttpRoutes.of[IO] {
    case GET -> Root / "videotag" => {
        import ctx._
        val q=quote{
          query[VideoTag]
        }
        val out1=ctx.run(q)
        Ok(out1.asJson)
    }.recoverWith{
      case ex=>
        ex.printStackTrace()
        InternalServerError("yikes")
    }
  }


  private val httpApp=(helloWorldService <+> greetService
    <+> videos <+> tags <+> videotags).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
  //  .withServiceErrorHandler(error)
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

  // add doobie/quill endpoints
  // H2 or postgres?
  // unit test
  // flywaydb

}
