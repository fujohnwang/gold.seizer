package config

import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
class GlobalConfig() {
  @BeanProperty
  var delaySeconds: Int = 60
  @BeanProperty
  var taskInterval: Int = 60
}

