package org.talend.example.commands;

public class ListCommand extends JiraCommand {
	String project;
	String user;

	public ListCommand(String name, String command, String project, String user) {
		super(name, command);
		this.project = project;
		this.user = user;
	}

	public String getProject() {
		return project;
	}

	public String getUser() {
		return user;
	}

}
