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
import java.security.GeneralSecurityException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.direct.DirectConsumer;
import org.apache.camel.processor.DelegateProcessor;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HelloWorld consumer.
 */
public class HelloWorldConsumer extends DirectConsumer {
    private static final transient Logger LOG = LoggerFactory.getLogger(HelloWorldConsumer.class);

    public HelloWorldConsumer(HelloWorldEndpoint endpoint, Processor processor) {
        super(endpoint, new DelegateProcessor(processor) {
        	@Override
        	protected void processNext(Exchange exchange) throws Exception {
        		validate(exchange);
        		decode(exchange);
        		super.processNext(exchange);
        	}
        });
    }

    @Override
    public Processor getProcessor() {
    	return super.getProcessor();
    }

    private static void decode(Exchange exchange) {
    	LOG.info("HelloWorldConsumer: received '{}'", exchange.getIn().getBody());
    	exchange.getIn().setBody(HelloWorldEndpoint.codec((String)exchange.getIn().getBody()));
    	LOG.info("HelloWorldConsumer: requesting '{}'", exchange.getIn().getBody());
    }

    private static void validate(Exchange exchange) throws UnsupportedEncodingException, GeneralSecurityException {
    	String request = (String)exchange.getIn().getBody();
    	ObjectHelper.notEmpty(request, "Input Body");	// throws IllegalArgumentException
    	LOG.info("HelloWorldConsumer: validating '{}'", request);
    	if (request.startsWith("!")) {
    		exchange.getIn().setBody("Goodbye World");
    		throw new RuntimeCamelException("message", new UnsupportedEncodingException("Cannot start with a '!'"));
    	}
	}
}
