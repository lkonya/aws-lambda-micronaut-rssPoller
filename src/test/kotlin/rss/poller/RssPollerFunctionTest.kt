package rss.poller

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals
import rss.poller.model.RssFeed


class RssPollerFunctionTest : Spek({

    describe("rss-poller function") {
        val server = ApplicationContext.run(EmbeddedServer::class.java)
        val client = server.applicationContext.getBean(RssPollerClient::class.java)

        it("should return 'rss-poller'") {
            assertEquals(emptyList<RssFeed>(), client.get().blockingGet())
        }

        afterGroup {
            server.stop()
        }
    }
})
