package org.tjpower

import javax.enterprise.context.ApplicationScoped

data class OutputObject (val result: String, val requestId: String)
data class InputObject (val name: String, val greeting: String)

@ApplicationScoped
class ProcessingService {


    fun process(input: InputObject, requestId: String): OutputObject {
        require(input.name != "Stuart") { CAN_ONLY_GREET_NICKNAMES }

        val result: String = input.greeting + " " + input.name

        return OutputObject(result, requestId)
    }

    companion object {
        const val CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames"
    }
}