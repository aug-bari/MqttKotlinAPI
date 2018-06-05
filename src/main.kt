class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("This is just a test")

            val topics = arrayOf("test")
            val client = Mqtt("tcp://broker.shiftr.io", "testme", topics)
            client.connect("try", "try")

            client.disconnect()
        }
    }
}