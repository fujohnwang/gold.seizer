import java.util.concurrent.TimeUnit

import akka.actor.Cancellable
import config.GlobalConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.support.{AbstractApplicationContext, FileSystemXmlApplicationContext}
import play.api.libs.concurrent.Akka
import play.api.{Application, GlobalSettings, Logger}


object Global extends GlobalSettings {

  import play.api.Play.current
  import play.api.libs.concurrent.Execution.Implicits._
  import scala.concurrent.duration._

  var cancellable: Cancellable = _
  var context: ApplicationContext = _

  override def onStart(app: Application): Unit = {
    super.onStart(app)

    context = new FileSystemXmlApplicationContext(app.configuration.getString("spring.context.location").getOrElse("conf/*.spring.xml"))

    val globalCfg = context.getBean(classOf[GlobalConfig])

    cancellable = Akka.system.scheduler.schedule(globalCfg.delaySeconds.second, globalCfg.taskInterval.second, new Runnable {
      override def run(): Unit = {
        Logger.info("run scheduled task...")
      }
    })
  }

  override def onStop(app: Application): Unit = {
    while (!(cancellable.isCancelled || cancellable.cancel())) {
      Logger.info("sleep 1 second to wait task cancel.")
      TimeUnit.SECONDS.sleep(1)
    }

    context.asInstanceOf[AbstractApplicationContext].destroy()

    super.onStop(app)
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = context.getBean(controllerClass)
}