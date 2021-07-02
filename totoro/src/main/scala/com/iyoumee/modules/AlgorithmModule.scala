package com.jtb.demo.ordermain.modules

import com.google.inject.{Provides, Singleton}
import com.iyoumee.modules.RuleSystem
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.inject.TwitterModule


object AlgorithmModule extends AlgorithmModule


class AlgorithmModule extends TwitterModule {

  @Singleton
  @Provides
  @RuleSystem
  def tccService(): Service[Request, Response] = {
    val client = Http.client
      .withSessionPool.maxSize(16)
      .withSessionPool.maxWaiters(0)
      .newService("172.26.133.197:20200", "")
    client
  }

}
