package org.talend.camel.template.simple;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMainSpring {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("appContext.xml");
        System.in.read();
    	appContext.close();
    }

}
