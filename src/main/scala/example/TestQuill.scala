package example

import io.getquill.{H2JdbcContext, SnakeCase}
import org.flywaydb.core.Flyway

object TestQuill extends App{

//  val pgDataSource = new org.postgresql.ds.PGSimpleDataSource()
//  pgDataSource.setUser("postgres")
//  pgDataSource.setPassword("chromestar52")
//  pgDataSource.setDatabaseName("esl")
//  val config = new HikariConfig()
//  config.setDataSource(pgDataSource)


  lazy val ctx = new H2JdbcContext(SnakeCase, "ctx")

  import ctx._

  implicit val videoInsertMeta = insertMeta[Video](_.id)
  implicit val tagInsertMeta = insertMeta[Tag](_.id)
  implicit val videotagInsertMeta = insertMeta[VideoTag](_.id)

  val flyway = Flyway.configure.dataSource(ctx.dataSource).load

  println(flyway.migrate())

  val out2=ctx.run(query[Video].insert(Video(0,"newvid")).returningGenerated(_.id))
  println(out2)

  println(ctx.run(query[Tag].insert(Tag(0,"mytag1")).returningGenerated(_.id)))
  println(ctx.run(query[Tag].insert(Tag(0,"mytag2")).returningGenerated(_.id)))
  println(ctx.run(query[Tag].insert(Tag(0,"mytag3")).returningGenerated(_.id)))

  val q=quote{
    query[Video]
  }

  val out=ctx.run(q)

  println(out)

//
//  ctx.transaction {
//    ctx.run(query[City].filter(_.name == "test").delete)
//    val ins: Long = ctx.run(query[City].insert(City(20000, "test", "TST", "Test County", 0)))
//  }
//  ctx.run(query[City].filter(_.name == "test").map(c=> c.countryCode)).foreach(println)

//  ctx.run(
//    query[City]
//      .join(query[Country])
//      .on{case (city, country) => city.countryCode == country.code}
//      .filter{case (city, country) => country.continent == "Asia"}
//      .map{case (city, country) => (country.name,city.name)
//      }
//  ).foreach(println)

}
