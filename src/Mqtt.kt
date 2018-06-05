import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

/**
 * High-Level MQTT API for Kotlin
 * made to hide MQTT inner workings and be easy to use.
 *
 * Based on Paho MQTT Java API.
 * Use the original documentation to implement MqttCallback.
 *
 * @param broker Broker URI in a standard form (such as "tcp://mywebsite.com").
 * @param clientName Name of the client to be identified in the network.
 * @param topics A list of topics to subscribe as soon as the client is connected.
 */
class Mqtt(var broker: String,
           clientName: String,
           var topics: Array<String> = arrayOf(),
           persistence: MemoryPersistence = MemoryPersistence())
        : MqttClient(broker, clientName, persistence) {

    /**
     * Establish a connection with the MQTT broker using default parameters.
     */
    override fun connect() {
        val connectionOptions = MqttConnectOptions()

        /// Make sure the session is clean for a first connect.
        //connectionOptions.isCleanSession = true

        connectClient(connectionOptions)
    }

    /**
     * Establish a connection with the MQTT broker if authentication is required.
     *
     * @param username Authentication username given by the provider.
     * @param password Authentication passphrase given by the provider.
     */
    fun connect(username: String, password: String) {
        val connectionOptions = MqttConnectOptions()

        /// Make sure the session is clean for a first connect.
        //connectionOptions.isCleanSession = true
        connectionOptions.userName = username
        connectionOptions.password = password.toCharArray()

        connectClient(connectionOptions)
    }

    /**
     * Private method to establish a connection to the MQTT broker
     * with the configuration given by the user.
     *
     * @param connectOptions The options given by the user.
     */
    private fun connectClient(connectOptions: MqttConnectOptions) {
        try {
            super.connect(connectOptions)
            if (topics.isNotEmpty())
                subscribe(topics)

            println("Connected.")
        } catch(e: MqttException) {
            println("Could not connect to $broker: ${e.localizedMessage}")
        }
    }

    /**
     * Subscribe to a topic.
     *
     * @param topic The MQTT topic to subscribe to.
     */
    override fun subscribe(topic: String) {
        topics += topic
        super.subscribe(topic)
    }

    /**
     * Subscribe to a set of topics.
     *
     * @param topics MQTT topics to subscribe to.
     */
    override fun subscribe(topics: Array<String>) {
        this.topics += topics
        super.subscribe(topics)
    }

    /**
     * Disconnect from the MQTT broker.
     */
    override fun disconnect() {
        try {
            if (isConnected) {
                super.disconnect()
                println("Disconnected.")
            }
        } catch(e: MqttException) {
            println("Could not disconnect from $broker: ${e.localizedMessage}")
        }
    }

    /**
     * Send a text message to a set of MQTT topics.
     *
     * @param topics A set of topics to send the message to.
     * @param payload The textual message to send.
     */
    fun send(topics: Array<String>, payload: String) {
        val message = MqttMessage()
        message.payload = payload.toByteArray()

        for (topic in topics) {
            super.publish(topic, message)
        }
    }
}