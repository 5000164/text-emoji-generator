package jp._5000164.slack_emoji_generator.domain

import org.scalajs.dom.html.Canvas

case class State(
                  canvas: Option[Canvas],
                  text: String
                )
