package actors

import akka.actor.{Props, ActorRef, ActorLogging, Actor}
import org.apache.commons.lang3.StringUtils
import play.api.libs.concurrent.Akka
import play.api.libs.ws.WS

import scala.util.Random

case class SpiderTaskSetting(url: String, intervalLowWatermark: Int = 60, intervalHighWatermark: Int = 600)

case object SpiderTaskDone

class Spider extends Actor with ActorLogging {

  import play.api.Play.current
  import play.api.libs.concurrent.Execution.Implicits._

  var lastContent: String = _

  override def receive = {

    case url: String => WS.url(url).get().map(response => {
      response.status match {
        case 200 => {
          if (!StringUtils.equals(response.body, lastContent)) {
            println(s"new content: ${response.body}")
          }
        }
        case _ => println("error to crawl the content ")
      }
    })
    case _ => log.warning("unexpected message")
  }
}


class SpiderScheduler extends Actor with ActorLogging {

  import play.api.Play.current
  import play.api.libs.concurrent.Execution.Implicits._
  import scala.concurrent.duration._

  var setting: SpiderTaskSetting = _

  var random: Random = new Random()

  val spider = Akka.system.actorOf(Props[Spider])

  override def receive = {
    case taskSetting: SpiderTaskSetting => {
      setting = taskSetting
      Akka.system.scheduler.scheduleOnce(setting.intervalLowWatermark.second, spider, setting.url)
    }
    case SpiderTaskDone => {
      val interval = random.nextInt(setting.intervalHighWatermark)
      Akka.system.scheduler.scheduleOnce((if (interval < setting.intervalLowWatermark) setting.intervalLowWatermark else interval).second, spider, setting.url)
    }
    case e@_ => log.warning("unexpected message: {}", e)
  }
}
