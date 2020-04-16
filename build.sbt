name := "http4s-db-example"

version := "0.1"

scalaVersion := "2.13.1"

val http4sVersion = "0.21.2"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.13.0",
  "io.circe" %% "circe-literal" % "0.13.0"
)

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.200",
  "io.getquill" %% "quill-jdbc" % "3.5.1",
  "org.flywaydb" % "flyway-core" % "6.3.2"
)