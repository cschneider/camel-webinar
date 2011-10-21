package org.talend.example;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraProcessTest extends CamelTestSupport {
    static final transient Logger LOG = LoggerFactory.getLogger(JiraProcessTest.class);

    @Test
    public void testJiraList() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(14);
        
        template.sendBody("direct:list-issues", "list CAMEL issues for hadrian");

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testJiraGet() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMessageCount(2);
        
        template.sendBody("direct:get-issue", "get CAMEL-4256");

        assertMockEndpointsSatisfied();
    }
    
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new JiraRouteBuilder();
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
    	CamelContext context = super.createCamelContext();
    	PropertiesComponent properties = new PropertiesComponent("classpath:devel-jira.properties");
    	properties.setCamelContext(context);
    	context.addComponent("properties", properties);
    	return context;
    }

}
