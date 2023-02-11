enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)

name := "Text Emoji Generator"

version := "1.4.1"

scalaVersion := "2.13.10"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")

if (sys.env.getOrElse("TEXT_EMOJI_GENERATOR_ENV", "production") == "production") {
  scalacOptions ++= Seq("-Xelide-below", "OFF")
} else {
  scalacOptions ++= Seq()
}

scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "2.1.1"
libraryDependencies += "com.github.japgolly.scalacss" %%% "core" % "1.0.0"
libraryDependencies += "com.github.japgolly.scalacss" %%% "ext-react" % "1.0.0"

npmDependencies in Compile ++= Seq(
  "react" -> "17.0.2",
  "react-dom" -> "17.0.2")

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14"
libraryDependencies += "org.scalatest" %% "scalatest-featurespec" % "3.2.14" % "test"
