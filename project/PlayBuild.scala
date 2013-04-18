import sbt.Keys._
import sbt._
import Dependencies._

/**
 * @author Mle
 */

object PlayBuild extends Build {
  lazy val pp = play.Project("playapp",
    path = file("."),
    applicationVersion = "0.1",
    dependencies = Seq(utilDep, utilActor, utilJdbc),
    settings = commonSettings)
  // hack to make sbt-idea and play 2.1 plugins work
  override lazy val settings = super.settings ++ com.typesafe.sbtidea.SbtIdeaPlugin.ideaSettings
  val commonSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.10.0",
    retrieveManaged := false,
    // system properties seem to have no effect in tests,
    // causing e.g. tests requiring javax.net.ssl.keyStore props to fail
    // ... unless fork is true
    sbt.Keys.fork in Test := true
  )
}