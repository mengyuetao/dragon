package  com.iyoumee.controllers

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.inject.Logging
import javax.inject.Inject
import com.twitter.util.{Future, FuturePool}
import com.twitter.util.Promise

class TranController @Inject()() extends Controller with Logging {


  get("/helloNlp") { request: Request =>
    response.ok.body("this is jack, from helloNlp!")
  }

}
