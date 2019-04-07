package rss.poller.model

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Introspected
data class RssFeeds(val feeds: List<RssFeed>)

@Introspected
data class RssFeed(
        val title: String,
        val description: String,
        val link: String,
        val publishedDate: LocalDateTime)


internal fun List<SyndEntry>.toRssFeeds() =
        this.map {
            RssFeed(
                    it.title ?: "",
                    (it.description?.value ?: "").trim(),
                    it.link,
                    it.publishedDate.toLocalDateTime()
            )
        }

internal fun SyndFeed.typedEntries(): List<SyndEntry> =
        (this.entries as List<SyndEntry?>).mapNotNull { it }

private fun Date?.toLocalDateTime(): LocalDateTime =
        this?.run {
            Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime()
        } ?: LocalDateTime.now()
