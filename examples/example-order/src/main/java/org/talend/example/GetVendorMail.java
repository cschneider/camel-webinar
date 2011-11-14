package org.talend.example;



import java.util.HashMap;
import java.util.Map;

import org.apache.camel.language.XPath;

public class GetVendorMail {
	Map<String, String> mailMap = new HashMap<String, String>();

	public GetVendorMail() {
		mailMap.put("Haendler1", "cameltest@die-schneider.net");
	}
	
	public String getMail(@XPath("/item/@vendor") String vendor) {
		return mailMap.get(vendor);
	}
}
