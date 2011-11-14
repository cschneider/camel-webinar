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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.Main;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.persistence.EntityManagerFactoryImpl;
import org.apache.openjpa.persistence.OpenJPAEntityManagerSPI;

/**
 * A Camel Router
 */
public class JpaExampleRouteBuilder extends RouteBuilder {

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
        from("jpa:org.apache.camel.examples.Customer").bean(new CustomerTransformer()).to("log:test");
    }
}
