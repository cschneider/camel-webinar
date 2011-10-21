package org.talend.example.commands;

public class JiraCommand {
	private String name;
	private String command;

	public JiraCommand(String name, String command) {
		super();
		this.name = name;
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public String getCommand() {
		return command;
	}

}
