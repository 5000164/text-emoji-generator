enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "slack-emoji-generator"

version := "0.1"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.1.1"
libraryDependencies += "com.github.japgolly.scalacss" %%% "core" % "0.5.3"
libraryDependencies += "com.github.japgolly.scalacss" %%% "ext-react" % "0.5.3"

npmDependencies in Compile ++= Seq(
  "react" -> "15.6.1",
  "react-dom" -> "15.6.1")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
