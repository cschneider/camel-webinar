package org.talend.example;

import junit.framework.Assert;

import org.junit.Test;
import org.talend.example.commands.GetCommand;
import org.talend.example.commands.ListCommand;

public class CommandParserTest {
	@Test
	public void testGet() {
		GetCommand command = (GetCommand) new CommandParser().parse("camelbot:get CAMEL-4500");
		Assert.assertEquals("camelbot", command.getName());
		Assert.assertEquals("get", command.getCommand());
		Assert.assertEquals("CAMEL-4500", command.getIssue());
	}
	
	@Test
	public void testList() {
		ListCommand command = (ListCommand) new CommandParser().parse("camelbot:list CAMEL hadrian");
		Assert.assertEquals("camelbot", command.getName());
		Assert.assertEquals("list", command.getCommand());
		Assert.assertEquals("CAMEL", command.getProject());
		Assert.assertEquals("hadrian", command.getUser());
	}
}
