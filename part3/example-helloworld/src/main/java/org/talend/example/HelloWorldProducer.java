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

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.component.direct.DirectProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HelloWorld producer.
 */
public class HelloWorldProducer extends DirectProducer {
    private static final transient Logger LOG = LoggerFactory.getLogger(HelloWorldProducer.class);

    public HelloWorldProducer(HelloWorldEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
    	encode(exchange);
    	LOG.info("HelloWorldProducer: sync send request");
    	super.process(exchange);
    }

    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
    	encode(exchange);
    	LOG.info("HelloWorldProducer: async send request");
    	return super.process(exchange, callback);
    }
    
    private static void encode(Exchange exchange) {
    	LOG.info("HelloWorldProducer: received '{}'", exchange.getIn().getBody());
    	exchange.getIn().setBody(HelloWorldEndpoint.codec((String)exchange.getIn().getBody()));
    	LOG.info("HelloWorldProducer: requesting '{}'", exchange.getIn().getBody());
    }
}
