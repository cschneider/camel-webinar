package org.talend.camel.example.dataformats;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class DataFormatsMain {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();
    
        context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("file:target/in?noop=true")
				.unmarshal().csv()
				.bean(new PrinterBean())
				.to("file:target/out");
			
			}
		});
        context.start();
        System.in.read();
        context.shutdown();
    }
    
}
