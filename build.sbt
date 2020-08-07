import sbtassembly.AssemblyPlugin.autoImport.assemblyMergeStrategy

ThisBuild / version := "19.1.0"
ThisBuild / scalaVersion := "2.12.8"


lazy val buildSettings = Seq(
  version := "19.1.0",
  scalaVersion := "2.12.8",
)

lazy val versions = new {
  val guice = "4.1.0"
  val logback = "1.2.3"
  val mockito = "1.9.5"
  val scalaTest = "3.0.8"
  val specs2 = "2.4.17"
  val finatra="19.12.0"
}

lazy val scalaCompilerOptions = scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Xlint",
  "-Ywarn-unused-import"
)

lazy val baseSettings = Seq(
  libraryDependencies ++= Seq(
    "org.mockito" % "mockito-core" %  versions.mockito % "test",
    "org.scalatest" %% "scalatest" %  versions.scalaTest % "test",
    "org.specs2" %% "specs2-core" % versions.specs2 % "test",
    "org.specs2" %% "specs2-junit" % versions.specs2 % "test",
    "org.specs2" %% "specs2-mock" % versions.specs2 % "test"
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  scalaCompilerOptions,
  javacOptions in (Compile, compile) ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked")
)

lazy val baseServerSettings = baseSettings ++ buildSettings ++ Seq(
  organization := "com.twitter",
  publishArtifact := false,
  publishLocal := {},
  publish := {},
  assemblyMergeStrategy in assembly := {
    case "BUILD" => MergeStrategy.discard
    case "META-INF/io.netty.versions.properties" => MergeStrategy.last
    case "META-INF\\io.netty.versions.properties" => MergeStrategy.last
    case other => MergeStrategy.defaultMergeStrategy(other)
  }

)


lazy val exampleServerSettings = baseServerSettings ++ Seq(
  //fork in run := true,
  javaOptions in Test ++= Seq("-Dlog.service.output=/dev/stdout", "-Dlog.access.output=/dev/stdout", "-Dlog_level=INFO"),
  excludeDependencies ++= Seq(
    // commons-logging is replaced by jcl-over-slf4j
    ExclusionRule("commons-logging", "commons-logging")
  )

)

lazy val root = (project in file("."))
  .settings(exampleServerSettings)
 .settings(
    name := "dragon",
    moduleName := "dragon",
  )


lazy val totoro = (project in file("totoro"))
  .settings(exampleServerSettings)
  .settings(
    name := "totoro",
    moduleName := "totoro",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finatra-http" % versions.finatra,
      "com.twitter" %% "finatra-http" % versions.finatra % "test",
      "com.twitter" %% "finatra-httpclient" % versions.finatra,
      "com.twitter" %% "finatra-httpclient" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "finatra-http" % versions.finatra % "test"  classifier "tests",
      "com.twitter" %% "inject-core" % versions.finatra % "test"  ,
      "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-server" % versions.finatra % "test"  ,
      "com.twitter" %% "inject-server" % versions.finatra % "test"  classifier "tests",
      "com.twitter" %% "inject-app" % versions.finatra % "test"  ,
      "com.twitter" %% "inject-app" % versions.finatra % "test"  classifier "tests",
      "com.twitter" %% "inject-modules" % versions.finatra % "test"  ,
      "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
      "org.scalatest" %% "scalatest" %  versions.scalaTest % "test",
      "com.google.inject" % "guice" % versions.guice  % "test",
      "com.google.inject.extensions" % "guice-testlib" %  versions.guice % "test",
      "org.mockito" % "mockito-core" %   versions.mockito  % "test",
      "ch.qos.logback" % "logback-classic" % versions.logback,

    )
  )
