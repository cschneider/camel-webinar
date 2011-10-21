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
import org.talend.example.commands.CommandParser;

/**
 * A Camel Router
 */
public class JiraRouteBuilder extends RouteBuilder {

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
    	getContext().setTracing(true);
    	from("{{irc.uri}}?nickname={{irc.bot.nick}}")
            .to("log:before")
            .setHeader("command", bean(new CommandParser()))
            .filter(simple("${header.command.name} == 'camelbot'"))
            .to("log:before")
            .recipientList(simple("direct:command-${header.command.command}")).ignoreInvalidEndpoints();
        
        from("direct:command-get")
	        .recipientList(simple("https://issues.apache.org/jira/si/jira.issueviews:issue-xml/${header.command.issue}/${header.command.issue}.xml?field=title&field=description"))
	    	.split(xpath("/rss/channel/item"))
	    	.setHeader("jira-title", xpath("/item/title/text()"))
	    	.setHeader("jira-description").xpath("/item/description/text()")
	    	.setBody(simple(" ${header.jira-title}"))
            .to("{{reply.uri}}")
	    	.setBody(simple(" ${header.jira-description}"))
	    	.bean(new XmlDecoder())
	        .to("{{reply.uri}}");

        from("direct:command-list")
	        .recipientList(simple("http://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml"
	        	    + "?jqlQuery=project+%3D+${header.command.project}+AND+resolution+%3D+Unresolved+AND+assignee+%3D+${header.command.user}"))
	    	.split(xpath("/rss/channel/item"))
	    	.setHeader("jira-title", xpath("/item/title/text()"))
	    	.setHeader("jira-assignee").xpath("/item/assignee/@username")
	    	.setHeader("jira-fixversion", xpath("/item/fixVersion/text()"))
	    	.setHeader("jira-link").xpath("/item/link/text()")
	    	.setBody(simple("Issue: ${header.jira-title} [assignee='${header.jira-assignee}', fixVersion='${header.jira-fixversion}'"))
            .to("{{reply.uri}}")
	    	.setBody(simple(" ${header.jira-link}"))
            .to("{{reply.uri}}");
    }
}
