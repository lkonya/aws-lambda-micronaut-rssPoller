package rss.poller

import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import rss.poller.model.RssFeeds
import rss.poller.service.RssPollerService
import java.util.function.Supplier
import javax.inject.Inject

@FunctionBean("rss-poller")
class RssPollerFunction : FunctionInitializer(), Supplier<RssFeeds> {

    @Inject
    private lateinit var rssPollerService: RssPollerService

    override fun get(): RssFeeds {
        val feeds = rssPollerService.sendRssItemsIntoSqS()
        return RssFeeds(feeds)
    }
}
