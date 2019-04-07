package rss.poller.configuration

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import javax.inject.Singleton

@Factory
class AwsSpecificBeans {

    @Bean
    @Singleton
    fun sqsClient(): SqsClient {
        return SqsClient.builder().region(Region.EU_CENTRAL_1).build()
    }
}
