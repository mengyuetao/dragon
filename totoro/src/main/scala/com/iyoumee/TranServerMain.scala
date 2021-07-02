package com.iyoumee

import com.iyoumee.controllers.TranController
import com.jtb.demo.ordermain.modules.AlgorithmModule
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter


object TranServerMain extends TranServer
class TranServer extends HttpServer {

  override def defaultHttpPort: String = ":38888"

  override def defaultAdminPort: Int = 39990

  override val modules = Seq(AlgorithmModule)

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[TranController]
  }
}
