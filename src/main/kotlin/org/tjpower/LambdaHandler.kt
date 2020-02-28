package org.tjpower

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import javax.inject.Inject
import javax.inject.Named

@Named("lambda")
class LambdaHandler : RequestHandler<InputObject, OutputObject> {
    @Inject
    lateinit var service: ProcessingService

    override fun handleRequest(input: InputObject, context: Context): OutputObject {
        return service.process(input, context.awsRequestId)
    }
}
