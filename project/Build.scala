import sbt.Keys._
import sbt._
import Dependencies._

/**
 * @author Mle
 */

object GitBuild extends Build {
  // hack to make sbt-idea and play 2.1 plugins work
  override lazy val settings = super.settings ++ com.typesafe.sbtidea.SbtIdeaPlugin.ideaSettings
  val credentialPath = Path.userHome / ".sbt" / "credentials.txt"
  val credentialSettings =
    if (credentialPath.exists())
      Seq(credentials += Credentials(credentialPath))
    else Seq.empty
  val commonSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.mle",
    version := "0.68-SNAPSHOT",
    scalaVersion := "2.10.0",
    retrieveManaged := false,
    publishTo := Some(Resolver.url("my-sbt-releases", new URL("http://xxx/artifactory/my-sbt-releases/"))(Resolver.ivyStylePatterns)),
    publishMavenStyle := false,
    // system properties seem to have no effect in tests,
    // causing e.g. tests requiring javax.net.ssl.keyStore props to fail
    // ... unless fork is true
    sbt.Keys.fork in Test := true,
    // the jars of modules depended on are not included unless this is true
    exportJars := true
  ) ++ credentialSettings

  // last 2.9.2 is 0.67-SNAPSHOT
  // 0.67-SNAPSHOT is an sbt plugin
  lazy val pp = play.Project("playapp",
    path = file("."),
    applicationVersion = "0.1",
    dependencies = Seq(utilActor, utilJdbc),
    settings = commonSettings)
    .settings(exportJars := false)
}