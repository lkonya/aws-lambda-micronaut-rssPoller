package rss.poller

import io.micronaut.function.FunctionBean
import io.micronaut.function.executor.FunctionInitializer
import java.util.function.Function

@FunctionBean("rss-poller")
class RssPollerFunction : FunctionInitializer(), Function<RssPoller, RssPoller> {

    override fun apply(msg: RssPoller): RssPoller {
        return msg
    }
}

/**
 * This main method allows running the function as a CLI application using: echo '{}' | java -jar function.jar
 * where the argument to echo is the JSON to be parsed.
 */
fun main(args: Array<String>) {
    val function = RssPollerFunction()
    function.run(args) { context -> function.apply(context.get(RssPoller::class.java)) }
}