package controllers

import play.api.libs.iteratee.{Concurrent, Iteratee}
import play.api.mvc._
import com.mle.util.Log


/**
 *
 * @author Mle
 */
object WebSockets extends Controller with Log {
  type ClientChannel = Concurrent.Channel[String]

  def index = Action {
    implicit request => Ok(views.html.wsIndex("yo"))
  }

  def webSocket = WebSocket.using(request => {
    val (out, channel) = Concurrent.broadcast[String]
    onConnect(channel, request)
    val in = Iteratee.foreach[String](onMessage(_, channel)).mapDone(_ => onClose(channel))
    (in, out)
  })

  def onConnect(channel: ClientChannel, request: RequestHeader) {
    log debug "IP: " + request.remoteAddress +
      "\nQuery string: " + request.queryString +
      "\nMethod: " + request.method +
      "\nPath: " + request.path +
      "\nURI: " + request.uri +
      "\nVersion: " + request.version +
      "\nSession: " + request.session
  }

  def onClose(channel: ClientChannel) {
  }

  def onMessage(msg: String, channel: ClientChannel) {
    log info "Server got msg: " + msg + " from: " + channel
    channel push "Thanks for your message. I will repeat what you said: " + msg
  }
}
