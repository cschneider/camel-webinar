package org.talend.example.commands;

import org.apache.camel.RuntimeCamelException;

public class CommandParser {
	public JiraCommand parse(String commandSt) {
		String[] parts = commandSt.trim().split("(:| )+");
		if (parts.length < 2) {
			return null;
		}
		String name = parts[0];
		String command = parts[1];
		if ("get".equals(command)) {
			return new GetCommand(name, command, parts[2]);
		}
		if ("list".equals(command)) {
			return new ListCommand(name, command, parts[2], parts[3]);
		} else {
			throw new RuntimeCamelException("Unkown command syntax " + commandSt);
		}
	}
}
