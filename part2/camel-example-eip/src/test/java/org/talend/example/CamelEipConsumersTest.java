package org.talend.example;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.idempotent.MemoryIdempotentRepository;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class CamelEipConsumersTest extends CamelTestSupport {
	
    @Test
    public void testIdempotentConsumer() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(5);

        template.sendBodyAndHeader("direct:idempotent", "Hello World 1", "id", "msg-0");
        template.sendBodyAndHeader("direct:idempotent", "Hello World 2", "id", "msg-1"); 
        template.sendBodyAndHeader("direct:idempotent", "Hello World 3", "id", "msg-0"); // Ignored
        template.sendBodyAndHeader("direct:idempotent", "Hello World 4", "id", "msg-2");
        template.sendBodyAndHeader("direct:idempotent", "Hello World 5", "id", "msg-0"); // Ignored
        template.sendBodyAndHeader("direct:idempotent", "Hello World 6", "id", "msg-3");
        template.sendBodyAndHeader("direct:idempotent", "Hello World 7", "id", "msg-1"); // Ignored
        template.sendBodyAndHeader("direct:idempotent", "Hello World 8", "id", "msg-0"); // Ignored
        template.sendBodyAndHeader("direct:idempotent", "Hello World 9", "id", "msg-4");

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	
                from("direct:idempotent").idempotentConsumer(header("id"), MemoryIdempotentRepository.memoryIdempotentRepository())
                	.log(LoggingLevel.INFO, "big-brother", "Processing message with id='${header.id}' body='${body}'")
                	.to("mock:result");
                 
            }
        };
    }
}
