package p1nkun1c0rns.statusmetrics

import io.prometheus.client.Counter
import io.prometheus.client.Gauge
import io.quarkus.scheduler.Scheduled
import io.smallrye.mutiny.Multi
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.mutiny.core.Vertx
import io.vertx.mutiny.ext.web.client.WebClient
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Status(vertx: Vertx, val config: Config) {

    val webClient = WebClient.create(vertx, WebClientOptions().setDefaultPort(443).setSsl(true))

    val errorCounter = Counter.build("service_status_fetch_error", "Error counter for fetching statuses")
        .labelNames("service", "reason").register()
    val statusGauge =
        Gauge.build("service_status", "Status of a service component, values 0 (operational) to 3 (major_outage)")
            .labelNames("service", "component").register()

    @Scheduled(every = "30s")
    fun fetchStatus() {
        config.getConfig().subscribe().with { serviceConfig ->
            // https://www.githubstatus.com/api
            webClient.get(serviceConfig.url, "/api/v2/components.json")
                .putHeader("Accept", "application/json")
                .send()
                .onItem().produceMulti {
                    val json = it.bodyAsJsonObject()
                    val service = json.getJsonObject("page").getString("name")
                    Multi.createFrom().iterable(
                        json.getJsonArray("components").map {
                            val component = it as JsonObject
                            ServiceStatus(
                                service = service,
                                component = component.getString("name"),
                                status = StatusValue.valueOf(component.getString("status"))
                            )
                        })
                }
                .onFailure().invoke {
                    errorCounter.labels(serviceConfig.name, it.message).inc()
                }
                .subscribe().with {
                    statusGauge.labels(it.service, it.component).set(it.status.ordinal.toDouble())
                }
        }
    }
}

data class ServiceStatus(
    val service: String,
    val component: String,
    val status: StatusValue
)

enum class StatusValue {
    operational,
    degraded_performance,
    partial_outage,
    major_outage
}
