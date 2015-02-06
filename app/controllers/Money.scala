package controllers

import org.springframework.stereotype
import play.api.mvc.{Action, Controller}

@stereotype.Controller
class Money extends Controller {
  def index = Action{
    Ok("")
  }
}