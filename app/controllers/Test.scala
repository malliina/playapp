package controllers

import play.api.mvc.{Action, RequestHeader, WebSocket, Controller}
import com.mle.util.Log
import play.api.libs.iteratee.{Iteratee, Concurrent}
import views._
import play.api.libs.json.JsValue

/**
 * @author Michael
 */
object Test extends WSController with Log {
  def index = Action(implicit request => Ok(html.testPage()))
}

trait WSController extends Controller with Log {
  type Message = JsValue
  type Channel = Concurrent.Channel[Message]

  def ws = WebSocket.using(implicit request => {
    val (out, channel) = Concurrent.broadcast[Message]
    onConnect(channel)
    val in = Iteratee.foreach[Message](onMessage(_, channel)).mapDone(_ => onClose(channel))
    (in, out)
  })

  def onMessage(msg: Message, from: Channel) {
    log info s"Message: $msg"
    from push msg
  }

  def onConnect(channel: Channel)(implicit request: RequestHeader) {
    log info "Connection"
  }

  def onClose(channel: Channel) {
    log info "Closed"
  }
}
