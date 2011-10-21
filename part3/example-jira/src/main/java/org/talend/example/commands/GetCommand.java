package org.talend.example.commands;


public class GetCommand extends JiraCommand {

	private String issue;

	public GetCommand(String name, String command, String issue) {
		super(name, command);
		this.issue = issue;
	}
	
	public String getIssue() {
		return issue;
	}

}
