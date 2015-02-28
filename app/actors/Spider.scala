package actors

import akka.actor.{Actor, ActorLogging, Props}
import org.jsoup.Jsoup
import play.api.libs.ws.WS

import scala.concurrent.duration._
import scala.util.Random

case object Go

case class SpiderTaskSetting(url: String, intervalLowWatermark: Int = 60, intervalHighWatermark: Int = 600)


class Spider(setting: SpiderTaskSetting) extends Actor with ActorLogging {

  import play.api.Play.current
  import play.api.libs.concurrent.Execution.Implicits._


  var random: Random = new Random()
  var lastContent: String = _

  override def receive = {
    case Go => {
      WS.url(setting.url).get() map (response => {
        response.status match {
          case 200 => {
            val body = new String(response.body.getBytes("ISO-8859-1"), "UTF-8")
            val html = Jsoup.parse(body)
            println(s"new content: ${html.select("a.newslist_style").toString}")

            reschedule()
          }
          case status@_ => println(s"error to crawl the content: $status "); reschedule()
        }
      })
    }
    case _ => log.warning("unexpected message"); reschedule()
  }

  def reschedule() = {
    val interval = random.nextInt(setting.intervalHighWatermark)
    val adjustedInterval = if (interval < setting.intervalLowWatermark) setting.intervalLowWatermark else interval
    context.system.scheduler.scheduleOnce(adjustedInterval.second, self, Go)
  }
}

object Spider {
  def props(setting: SpiderTaskSetting): Props = Props(new Spider(setting))
}

