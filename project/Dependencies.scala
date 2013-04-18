import sbt._

/**
 * @author Mle
 */

object Dependencies {
  val utilVersion = "0.69-SNAPSHOT"
  val utilGroup = "com.github.malliina"
  val utilDep = utilGroup %% "util" % "0.701-SNAPSHOT"
  val utilActor = utilGroup %% "util-actor" % utilVersion
  val utilJdbc = utilGroup %% "util-jdbc" % utilVersion
}