package org.talend.example;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JiraProcessSpringTest extends CamelSpringTestSupport {
    static final transient Logger LOG = LoggerFactory.getLogger(JiraProcessSpringTest.class);

    @Test
    public void testJira() throws Exception {
        template.sendBody("{{irc.uri}}?nickname={{irc.user.nick}}", "camelbot: get CAMEL-4256");
        Thread.sleep(10000);
    }

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/camel-context.xml");
	}
}
