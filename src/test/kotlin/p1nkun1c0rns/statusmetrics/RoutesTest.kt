package p1nkun1c0rns.statusmetrics

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.smallrye.mutiny.Uni
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import java.time.Duration

@QuarkusTest
class RoutesTest {

    @Test
    fun testLiveRoute() {
        given()
            .get("/live")
            .then()
            .statusCode(200)
            .body(`is`("Live"))
    }

    @Test
    fun testReadyRoute() {
        given()
            .get("/ready")
            .then()
            .statusCode(200)
            .body(`is`("Ready"))
    }

    @Test
    fun testMetricsRoute() {
        given()
            .get("/metrics")
            .then()
            .statusCode(200)
    }

    @Test
    fun `long running test for execute metric capture`() {
        Uni.createFrom().item(Unit)
            .onItem().delayIt().by(Duration.ofSeconds(35))
            .map {
                given()
                    .get("/metrics")
                    .then()
                    .body(containsString("service_status_fetch_error"))
                    .body(containsString("service_status"))
            }.await().indefinitely()
    }
}
