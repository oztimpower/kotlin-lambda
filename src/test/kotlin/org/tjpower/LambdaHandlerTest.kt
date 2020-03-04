package org.tjpower

import io.quarkus.amazon.lambda.test.LambdaClient
import io.quarkus.test.junit.NativeImageTest
import io.quarkus.test.junit.QuarkusTest
import org.jboss.logging.Logger
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


@NativeImageTest
class LambdaSimpleTestCaseIT: LambdaSimpleTestCase() {
}

@QuarkusTest
class LambdaSimpleTestCase {

    private val LOG: Logger = Logger.getLogger(LambdaSimpleTestCase::class.java)

    val input = InputObject("Test-Tim", "Hello from Junit5")


    @Test
    fun unitTest() {
        LOG.info("Invoking unitTest()")

        try {
            val out: OutputObject = ProcessingService().process(input, "aws-request-1")
            LOG.info(out)

            Assertions.assertTrue(out.result.contains("Test-Tim"))
            Assertions.assertEquals("${input.greeting} ${input.name}", out.result)
            Assertions.assertTrue(out.requestId.matches(Regex("aws-request-\\d")))
        } catch (e: Exception) {
            Assertions.fail<Any>(e.message)
        }
    }

    @Test
    fun unitTestValidator() {
        LOG.info("Invoking unitTestValidator()")

        try {
            val input = InputObject("Stuart", "Default Greeting")
            val out: OutputObject = ProcessingService().process(input, "dummyRequest")
            LOG.info(out)

            Assertions.fail<Any>()
        } catch (e: Exception) {
            LOG.info(e.message)
            Assertions.assertEquals(ProcessingService.CAN_ONLY_GREET_NICKNAMES, e.message);
        }
    }

    @Test
    fun integrationTest() {
        LOG.info("Invoking integrationTest()")

        try {
            val out: OutputObject = LambdaClient.invoke(OutputObject::class.java, input)

            Assertions.assertTrue(out.result.contains("Test-Tim"))
        } catch (e: Exception) {
            Assertions.fail<Any>(e.message)
        }
    }

}

