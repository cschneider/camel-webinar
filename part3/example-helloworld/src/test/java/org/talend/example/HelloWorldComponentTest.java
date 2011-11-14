/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.talend.example;

import java.io.UnsupportedEncodingException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.processor.interceptor.Tracer;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class HelloWorldComponentTest extends CamelTestSupport {

    @Test
    public void testHelloWorldValidInput() throws Exception {
    	String request = "Hello Camel World";
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);       
        mock.expectedBodyReceived().constant(request);
        
        template.sendBody("helloworld://foo", request);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testHelloWorldRouteTryCatch() throws Exception {
    	String request = "Hello Camel World!";
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(1);       
        mock.expectedBodyReceived().constant("Some value");
        
        template.sendBody("direct://trycatch", request);

        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
            	Tracer tracer = new Tracer();
            	tracer.setLogName("HELLO-WORLD");
            	getContext().addInterceptStrategy(tracer);

            	from("helloworld://foo")
                    .to("log://in-flight")
                    .to("mock:result");

                from("direct://trycatch")
                    .to("log://in-flight")
                    .doTry()
                        .to("log:try")
                        .to("helloworld://bar")
                    .doCatch(UnsupportedEncodingException.class)
                        .setBody().constant("Some value")
                        .to("log:catch")
                    .end()
                    .to("mock:result");
                
                from("helloworld://bar")
                    .to("log:bar");
            }
        };
    }
}
