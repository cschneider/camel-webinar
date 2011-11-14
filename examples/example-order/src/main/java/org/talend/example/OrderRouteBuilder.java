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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;

/**
 * A Camel Router
 */
public class OrderRouteBuilder extends RouteBuilder {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main.main(args);
    }

    /**
     * Lets configure the Camel routing rules using Java code...
     */
    public void configure() {
    	GetVendorMail vendorMailService = new GetVendorMail();
        from("file:src/data?noop=true")
    	.setHeader("customer", xpath("/order/customer/text()").stringResult())
    	.split(xpath("/order/item"))
    	.setHeader("count", xpath("/item/@count").stringResult())
    	.setHeader("name", xpath("/item/@name").stringResult())    	
    	.choice()
    		.when(xpath("/item/@vendor != 'direct'")).to("direct:vendor")
    		.otherwise().recipientList(simple("file:target/messages/orders/${header.customer}"));
    from("direct:vendor")
    	.setHeader("to", bean(new GetVendorMail()))
    	.setHeader("subject", simple("Bestellung fuer ${header.customer}"))
    	.to("velocity:mailtemplate.txt")
    	.to("smtp:mail.die-schneider.net");
    }
}
