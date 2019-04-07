package rss.poller.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.context.annotation.Property
import rss.poller.client.RssFeedClient
import rss.poller.model.RssFeed
import rss.poller.model.toRssFeeds
import rss.poller.model.typedEntries
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequest
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry
import java.util.*
import javax.inject.Singleton

@Singleton
class RssPollerService(private val rssFeedClient: RssFeedClient,
                       private val sqsClient: SqsClient,
                       private val objectMapper: ObjectMapper,
                       @Property(name = "sqs.queue.url") private val queueUrl: String,
                       @Property(name = "sqs.message.batch-size") private val batchSize: Int,
                       @Property(name = "sqs.message.delay-between-batches") private val delayBetweenBatches: Int) {

    fun sendRssItemsIntoSqS(): List<RssFeed> {
        val rssFeeds = rssFeedClient.fetchRss().blockingFirst().typedEntries().toRssFeeds()
        rssFeeds.chunked(batchSize).forEachIndexed(::publishFeedsWithDelay)
        return rssFeeds
    }

    private fun publishFeedsWithDelay(index: Int, chunkedRssFeeds: List<RssFeed>) {
        val batchEntry = chunkedRssFeeds.map {
            SendMessageBatchRequestEntry.builder()
                    .id(UUID.randomUUID().toString())
                    .delaySeconds(index * delayBetweenBatches)
                    .messageBody(objectMapper.writeValueAsString(it))
                    .build()
        }
        val batchRequest = SendMessageBatchRequest.builder().queueUrl(queueUrl).entries(batchEntry).build()
        sqsClient.sendMessageBatch(batchRequest)
    }
}
