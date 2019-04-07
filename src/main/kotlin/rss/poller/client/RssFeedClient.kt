package rss.poller.client

import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader
import io.micronaut.context.annotation.Property
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RssFeedClient(@Client("\${rss.feed.baseurl}") @Inject private val httpclient: RxHttpClient,
                    @Property(name = "rss.feed.path") private val path: String) {

    fun fetchRss(): Flowable<SyndFeed> = httpclient.exchange(path)
                .map { SyndFeedInput().build(XmlReader(it.body()!!.toInputStream())) }
}
