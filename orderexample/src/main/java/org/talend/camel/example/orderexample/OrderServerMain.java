package org.talend.camel.example.orderexample;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderServerMain {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();
        context.setTracing(true);
        context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:target/ordersin?noop=true")
				.setHeader("customer", xpath("//order/customer").stringResult())
				.setHeader("orderSum", xpath("//order/sum").stringResult())
				.choice()
					.when(xpath("//order/sum>5000"))
					.to("file:target/bigorders")
					.setHeader("subject", simple("Neue Order von ${header.customer}"))
					.setBody(simple("Hallo Sales Team,\nneue Order von ${header.customer} in Höhe von ${header.ordersum} EUR erhalten"))
					.to("smtp:mail.liquid-reality.de?to=cameltest@liquid-reality.de")
				.otherwise()
					.to("file:target/smallorders");
			}
		});
        context.start();
        System.in.read();
        context.shutdown();
    }
    
}
