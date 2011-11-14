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

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.direct.DirectEndpoint;

/**
 * Represents a HelloWorld endpoint.
 */
public class HelloWorldEndpoint extends DirectEndpoint {
    public HelloWorldEndpoint() {
    }

    public HelloWorldEndpoint(String uri, HelloWorldComponent component) {
        super(uri, component);
    }

    public HelloWorldEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        return new HelloWorldProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new HelloWorldConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
    
    public static String codec(String request) {
    	return new StringBuffer(request).reverse().toString();
    }

}
