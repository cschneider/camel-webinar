package org.talend.example;

public class XmlDecoder  {
    public static String xmldecode(String content) {
    	return content.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }
}
