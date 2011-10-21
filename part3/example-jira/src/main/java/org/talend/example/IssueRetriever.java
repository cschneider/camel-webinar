package org.talend.example;

import org.apache.camel.Header;
import org.apache.camel.RecipientList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.example.commands.GetCommand;

public class IssueRetriever  {
    static final transient Logger LOG = LoggerFactory.getLogger(IssueRetriever.class);
    
    @RecipientList
    public String uri(@Header("command") GetCommand command) {
    	String result = "https://issues.apache.org/jira/si/jira.issueviews:issue-xml/" 
        	    + command.getIssue() + "/" + command.getIssue() + ".xml";
    	LOG.info("[GET] Retrieveing issue from {}", result);
    	return result;
    }
}
