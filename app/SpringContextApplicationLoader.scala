import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import play.api.inject.{ApplicationLifecycle, BindingKey, Injector}
import play.api.routing.Router
import play.api.{BuiltInComponentsFromContext, Application, ApplicationLoader}
import play.api.ApplicationLoader.Context

import scala.concurrent.Future

/**
 *
 * Created by fuqiangwang on 6/17/15.
 */
class SpringContextApplicationLoader extends ApplicationLoader with ApplicationLifecycle {

  val appCtx = new ClassPathXmlApplicationContext("classpath*:resources/spring/*.spring.xml")

  override def load(context: Context): Application = {
    new SpringEnabledComponents(context, appCtx).application
  }

  override def addStopHook(hook: () => Future[Unit]): Unit = {
    Future.successful {
      appCtx.destroy()
    }
  }
}


class SpringEnabledComponents(context: Context, appCtx: ApplicationContext) extends BuiltInComponentsFromContext(context) {
  override def router: Router = Router.empty

  val oldInjector = super.injector

  lazy override val injector: Injector = new SpringInjector(oldInjector, appCtx)
}

class SpringInjector(injector: Injector, appCtx: ApplicationContext) extends Injector {
  override def instanceOf[T](implicit manifest: ClassManifest[T]): T = injector.instanceOf(manifest)

  override def instanceOf[T](clazz: Class[T]): T = appCtx.getBean(clazz)

  override def instanceOf[T](key: BindingKey[T]): T = injector.instanceOf(key)
}
