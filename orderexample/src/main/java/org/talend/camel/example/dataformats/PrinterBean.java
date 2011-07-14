package org.talend.camel.example.dataformats;

import java.util.List;

public class PrinterBean {
	public String process(List<List<String>> lines) {
		for (List<String> line : lines) {
			System.out.println(String.format("Name: %20s,  IQ: %3s, Currently doing this: %s", line.toArray()));
		}

		return "Processed";
	}
}
