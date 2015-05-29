import actors.{Go, Spider, SpiderTaskSetting}
import org.springframework.context.ApplicationContext
import org.springframework.context.support.{AbstractApplicationContext, FileSystemXmlApplicationContext}
import play.api.libs.concurrent.Akka
import play.api.mvc.WithFilters
import play.api.{Application, GlobalSettings}
import play.filters.gzip.GzipFilter


object Boot extends WithFilters(new GzipFilter(shouldGzip = (request, response) => response.headers.get("Content-Type").exists(_.startsWith("text/html")))) with GlobalSettings {

  import play.api.Play.current

  var context: ApplicationContext = _

  override def onStart(app: Application): Unit = {
    super.onStart(app)

    context = new FileSystemXmlApplicationContext(app.configuration.getString("spring.ctx.location").getOrElse("conf/*.spring.xml"))



    val centralBankSpiderDriver = Akka.system.actorOf(Spider.props(SpiderTaskSetting("http://www.pbc.gov.cn/publish/goutongjiaoliu/524/index.html", intervalLowWatermark = 10, intervalHighWatermark = 60)), "central-bank-spider-driver")
    centralBankSpiderDriver ! Go

  }

  override def onStop(app: Application): Unit = {
    context.asInstanceOf[AbstractApplicationContext].destroy()

    super.onStop(app)
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = context.getBean(controllerClass)
}