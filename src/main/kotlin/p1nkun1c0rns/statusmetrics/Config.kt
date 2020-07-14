package p1nkun1c0rns.statusmetrics

import io.smallrye.mutiny.Multi
import io.vertx.core.json.JsonObject
import io.vertx.mutiny.core.Vertx
import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Config(private val vertx: Vertx, @ConfigProperty(name = "statusConfigFile") private val configFile: String) {

    fun getConfig() =
        vertx.fileSystem().readFile(configFile).onItem().produceMulti {
            Multi.createFrom().iterable(
                (it.toJsonArray() as Iterable<JsonObject>).map {
                    ServiceConfig(
                        name = it.getString("name"),
                        url = it.getString("url")
                    )
                }
            )
        }
}

data class ServiceConfig(
    val name: String,
    val url: String
)
