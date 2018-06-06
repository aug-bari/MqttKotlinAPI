package org.augbari

import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.StringSpec


class MqttTest : StringSpec() {
    init {
        val client = Mqtt("tcp://broker.shiftr.io", "testClient")

        client.connect("try", "try")
        client.isConnected.shouldBeTrue()

        client.disconnect()
        client.isConnected.shouldBeFalse()
    }
}