package org.talend.camel.template.simple;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ServerMain {

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
				from("file:target/in?noop=true")
				.to("file:target/out");
			}
		});
        context.start();
        System.in.read();
        context.shutdown();
    }

}
