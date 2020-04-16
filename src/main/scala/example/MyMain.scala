package example

import java.io.Closeable
import java.util.concurrent.Executors

import MyMain.{Greeting, ctx}
import cats.effect._
import cats.implicits._
import doobie.quill.DoobieContext
import doobie.util.transactor.Transactor
import doobie._
import doobie.implicits._

import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.literal._
import io.getquill.{H2JdbcContext, Literal, SnakeCase, UpperCase}
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.blaze._
import io.circe.generic.auto._
import io.getquill.monad.Effect
import javax.sql.DataSource
import org.http4s.server.ServiceErrorHandler
import org.http4s.EntityEncoder
import org.http4s.EntityEncoder._
import scala.concurrent.ExecutionContext
import cats.effect.IO

object MyMain extends IOApp {


  val ctx = new H2JdbcContext(SnakeCase, "ctx")

  private implicit val cs: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

  private val blockingPool = Executors.newFixedThreadPool(4)
  private val blocker = Blocker.liftExecutorService(blockingPool)

  val xa: Transactor[IO]= Transactor.fromDataSource[IO](ctx.dataSource,ExecutionContext.global,blocker)

  val dc = new DoobieContext.H2(SnakeCase) // Literal naming scheme

  import dc._

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
      val vids=dc.run{
        query[Video]
      }.transact(xa)
      Ok(vids.map(_.asJson))
    }// .recoverWith{errorhandler} // made global
  }

  private val tags = HttpRoutes.of[IO] {
    case GET -> Root / "tag" => {
      val tags=dc.run{
        query[Tag]
      }.transact(xa)
      Ok(tags.map(_.asJson))
    }
  }

//  private def error(req:Request[IO]) :ServiceErrorHandler[IO]=  ServiceErrorHandler{
//    //case th:Throwable=>
//    //th.printStackTrace()
//    IO(InternalServerError)
//  }

  val errorhandler: PartialFunction[Throwable, IO[Response[IO]]] ={
    case th: Throwable=>
      th.printStackTrace()
      InternalServerError(s"InternalServerError: $th")
  }

  private val videotags = HttpRoutes.of[IO] {
    case GET -> Root / "videotag" => {
      val tags=dc.run{
        query[VideoTag]
      }.transact(xa)
      Ok(tags.map(_.asJson))
    }
  }


  private val httpApp=(helloWorldService <+> greetService
    <+> videos <+> tags <+> videotags).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
    .withServiceErrorHandler(req=>errorhandler)
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

  // add doobie/quill endpoints
  // H2 or postgres?
  // unit test
  // flywaydb
  // https://typelevel.org/blog/2018/08/25/http4s-error-handling-mtl.html#http-error-handling

}
