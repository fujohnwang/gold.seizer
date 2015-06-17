import com.typesafe.sbt.packager.MappingsHelper._

lazy val root = (project in file(".")).enablePlugins(PlayScala)

organization := "afoo"

name := """gold.seizer"""

version := "1.0.0-SNAPSHOT"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalaVersion := "2.11.6"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Internal Maven Repository" at "http://repo.caimi-inc.com/nexus/content/groups/public/"

resolvers += "Central Maven Repository" at "http://repo1.maven.org/maven2/"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)
// libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"

libraryDependencies += "org.springframework" % "spring-context" % "4.1.6.RELEASE"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"

libraryDependencies += "org.apache.avro" % "avro" % "1.7.7"

libraryDependencies += "org.mapdb" % "mapdb" % "1.0.6"

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.webjars" % "react" % "0.13.3",
  "org.webjars" % "datatables" % "1.10.7"
)

mappings in Universal += file("ReleaseNote.md") -> "ReleaseNote.md"

mappings in Universal += file("LICENSE") -> "LICENSE"

mappings in Universal += file("README.md") -> "README.md"

mappings in Universal ++= directory("agents")

mappings in Universal ++= directory("conf")

routesGenerator := InjectedRoutesGenerator

