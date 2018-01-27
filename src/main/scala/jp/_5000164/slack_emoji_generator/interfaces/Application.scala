package jp._5000164.slack_emoji_generator.interfaces

import org.scalajs.jquery.jQuery

import scala.scalajs.js.annotation.JSExportTopLevel

object Application extends App {
  def setupUI(): Unit = {
    jQuery("body").append("<p>Hello World</p>")
    jQuery("body").append("<p id=\"click-me-button\">test</p>")
    jQuery("#click-me-button").click(() => addClickedMessage())
  }

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    jQuery("body").append("<p>clicked</p>")
  }

  jQuery(() => setupUI())
}
