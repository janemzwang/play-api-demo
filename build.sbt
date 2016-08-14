name := """play-api-demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "junit" % "junit" % "4.12" % "test",
  "mysql" % "mysql-connector-java" % "5.1.21",
  "com.google.code.gson" % "gson" % "2.3"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// no conf directory to be created in the distribution, and it will not be on the classpath.
// The contents of the applications conf directory will still be on the classpath by virtue of the fact that it’s included in the applications jar file.
// since we are using JPA, this must be set to false.
PlayKeys.externalizeResources := false

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"
