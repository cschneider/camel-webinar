package org.talend.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class CamelEipSplitAggregateTest extends CamelTestSupport {

    @SuppressWarnings("unchecked")
	@Test
    public void testSplitAndAggregate() throws Exception {
        MockEndpoint afterSplitter = getMockEndpoint("mock:afterSplitter");
        afterSplitter.expectedMessageCount(8); // after the splitter we expect each City object in its own message
        MockEndpoint vacation = getMockEndpoint("mock:vacation");
        vacation.expectedMessageCount(1);

        List<City> destinations = Arrays.asList(new City[] {
                new City("Athens", 10), 
                new City("Toronto", 7), 
                new City("Moscow", 12), 
                new City("San Diego", 8), 
                new City("Aruba", 0), 
                new City("London", 20), 
                new City("Venice", 4), 
                new City("Cancun", 0)});

        template.sendBody("direct:start", destinations);
        assertMockEndpointsSatisfied();

        // Extract the received cities out of the vacation mock endpoint
        Exchange out = vacation.getExchanges().get(0);
        List<City> cities = out.getIn().getBody(List.class);
        
        assertTrue(cities.size() == 2);
        String[] cityNames = {cities.get(0).name, cities.get(1).name};
        assertTrue(ArrayUtils.contains(cityNames, "Aruba"));
        assertTrue(ArrayUtils.contains(cityNames, "Cancun"));
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
	                .split().body() // body contains an array so the splitter will give us one message for each array element
	                .to("log:splitter")
	                .to("mock:afterSplitter")
                    .to("seda:travel");
                
                from("seda:travel")
                	.filter(simple("${in.body.museums} == 0")) // We want to do a beach holiday so the city should have no museums
                	.aggregate(constant(true), new BodyArrayAggregationStrategy()).completionSize(2).completionInterval(1000L)
                	.to("log:vacation-time")
                	.to("mock:vacation");
            }
        };
    }

    public static final class City {
        String name;
        int museums;             
        
        public int getMuseums() {
			return museums;
		}

		public City(String name, int museums) {
            this.name = name;
            this.museums = museums; 
        }

        @Override
        public String toString() {
        	return "Destination " + name  
        		+ (museums > 0 ? " featuring " + museums + " fine museums" : "");
        }
        
    }
    
    /**
     * The aggregate message has a List as the in body that contains
     * the bodies of all messages it aggregates  
     */
    public class BodyArrayAggregationStrategy implements AggregationStrategy {

		@Override
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			Exchange answer = oldExchange;
			if (oldExchange == null) {
				answer = new DefaultExchange(newExchange);
				answer.getIn().setBody(new ArrayList<Object>());
			}
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>)answer.getIn().getBody();
			list.add(newExchange.getIn().getBody());
			return answer;
		}
    	
    }
}
