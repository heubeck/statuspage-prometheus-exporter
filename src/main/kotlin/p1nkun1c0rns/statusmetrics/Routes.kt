package p1nkun1c0rns.statusmetrics

import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.RoutingExchange
import java.io.StringWriter
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Routes {

    @Route(path = "/live")
    fun live(re: RoutingExchange) = re.ok("Live")

    @Route(path = "/ready")
    fun ready(re: RoutingExchange) = re.ok("Ready")

    @Route(path = "/metrics", produces = [TextFormat.CONTENT_TYPE_004])
    fun metrics(re: RoutingExchange) {
        StringWriter().use {
            TextFormat.write004(it, CollectorRegistry.defaultRegistry.metricFamilySamples())
            re.ok(it.toString())
        }
    }
}