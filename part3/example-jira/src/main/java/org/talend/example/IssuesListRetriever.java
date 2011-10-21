package org.talend.example;

import org.apache.camel.Header;
import org.apache.camel.RecipientList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.example.commands.ListCommand;

public class IssuesListRetriever  {
    static final transient Logger LOG = LoggerFactory.getLogger(IssuesListRetriever.class);

    @RecipientList
    public String uri(@Header("command") ListCommand command) {
    	String result = String.format("http://issues.apache.org/jira/sr/jira.issueviews:searchrequest-xml/temp/SearchRequest.xml"
    	    + "?jqlQuery=project+%%3D+%s+AND+resolution+%%3D+Unresolved+AND+assignee+%%3D+%s", command.getProject(), command.getUser());
    	LOG.info("[LIST] Retrieveing issues from {}", result);
    	return result;
    }
}
