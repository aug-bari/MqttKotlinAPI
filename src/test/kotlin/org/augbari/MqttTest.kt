package org.augbari

import org.junit.Assert.assertEquals
import org.junit.Test

class MqttTest {
    @Test
    fun testConnection() {
        val client = Mqtt("tcp://broker.shiftr.io", "testClient")

        client.connect("try", "try")
        assertEquals(client.isConnected, true)

        client.disconnect()
        assertEquals(client.isConnected, false)
    }
}