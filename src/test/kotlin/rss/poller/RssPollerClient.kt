package rss.poller

import io.micronaut.function.client.FunctionClient
import io.reactivex.Single
import rss.poller.model.RssFeeds
import javax.inject.Named

@FunctionClient
interface RssPollerClient {

    @Named("rss-poller")
    fun get(): Single<RssFeeds>

}
