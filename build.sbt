import com.typesafe.sbt.SbtNativePackager._
import NativePackagerHelper._

lazy val root = (project in file(".")).enablePlugins(PlayScala)

organization := "afoo"

name := """gold.seizer"""

version := "1.0.0-SNAPSHOT"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

scalaVersion := "2.11.5"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Internal Maven Repository" at "http://repo.caimi-inc.com/nexus/content/groups/public/"

resolvers += "Central Maven Repository" at "http://repo1.maven.org/maven2/"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)

libraryDependencies ++= Seq(jdbc, anorm, cache, ws)

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.34"

libraryDependencies += "org.springframework" % "spring-context" % "4.1.4.RELEASE"

mappings in Universal += file("ReleaseNote.md") -> "ReleaseNote.md"

mappings in Universal += file("LICENSE") -> "LICENSE"

mappings in Universal += file("README.md") -> "README.md"

mappings in Universal ++= directory("agents")

mappings in Universal ++= directory("conf")
