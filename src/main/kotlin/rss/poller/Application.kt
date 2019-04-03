package rss.poller

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("rss.poller")
                .mainClass(Application.javaClass)
                .start()
    }
}