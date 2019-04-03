package rss.poller

import io.micronaut.function.client.FunctionClient
import io.micronaut.http.annotation.Body
import io.reactivex.Single
import javax.inject.Named

@FunctionClient
interface RssPollerClient {

    @Named("rss-poller")
    fun apply(@Body body: RssPoller): Single<RssPoller>

}
