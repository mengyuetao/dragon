resolvers ++= Seq(
  Classpaths.sbtPluginReleases,
  Resolver.sonatypeRepo("snapshots")
)


addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")
