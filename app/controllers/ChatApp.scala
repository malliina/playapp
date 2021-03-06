package controllers

import models.ChatRoom
import play.api.libs.json.JsValue
import play.api.mvc._
import views._
import com.mle.util.Log


/**
 *
 * @author Mle
 */
object ChatApp extends Controller with Log {
  def index = Action {
    implicit request =>
      Ok(html.chatIndex("Hoi!"))
  }

  def chatRoom(username: Option[String]) = Action {
    implicit request =>
      username.filterNot(_.isEmpty).map {
        username =>
          log info "User logged in: " + username
          Ok(views.html.chatRoom(username))
      }.getOrElse {
        Redirect(routes.ChatApp.index()).flashing(
          "error" -> "Please choose a valid username."
        )
      }
  }

  def chat(username: String) = WebSocket.async[JsValue] {
    request =>
      ChatRoom.join(username)
  }
}
