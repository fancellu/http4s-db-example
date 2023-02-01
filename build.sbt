name := "http4s-db-example"

version := "0.2"

scalaVersion := "2.13.10"

val http4sVersion = "0.23.16"

val doobieVersion= "1.0.0-RC2"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % "0.23.12",
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % "0.14.1",
  "io.circe" %% "circe-literal" % "0.14.1",
  "org.typelevel" %% "log4cats-slf4j"   % "2.5.0",
  "org.slf4j" % "slf4j-simple" % "1.7.36",
)

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.200",
  "org.tpolecat" %% "doobie-core"  % doobieVersion,
  "io.getquill" %% "quill-doobie" % "4.6.0",
  "org.flywaydb" % "flyway-core" % "8.5.9"
)

