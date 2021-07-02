package com.iyoumee.controllers

import com.iyoumee.modules.RuleSystem
import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.Controller
import com.twitter.inject.Logging
import javax.inject.Inject
import com.twitter.util.{Future, FuturePool}
import com.twitter.util.Promise

class TranController @Inject()(
                                @RuleSystem ruleSystemService: Service[Request, Response]
                              ) extends Controller with Logging {

  get("/helloNlp") { request: Request =>
    response.ok.body("this is jack, from helloNlp!")
  }

  //测试规则算法
  post("/nlp/HY_template_Match") { request: Request =>
    val response = ruleSystemService(request)
    response
  }


}
