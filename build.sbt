name := "http4s-db-example"

version := "0.2"

scalaVersion := "2.13.8"

val http4sVersion = "0.23.11"

val doobieVersion= "1.0.0-RC1"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.14.1",
  "io.circe" %% "circe-literal" % "0.14.1"
)

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.200",
  "org.tpolecat" %% "doobie-core"  % doobieVersion,
  "org.tpolecat" %% "doobie-quill" % doobieVersion,
  "org.flywaydb" % "flyway-core" % "8.5.9"
)

