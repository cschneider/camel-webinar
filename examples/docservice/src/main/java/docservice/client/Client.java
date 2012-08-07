package docservice.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;

import org.apache.cxf.jaxrs.client.WebClient;

public class Client {

    /**
     * @param args
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        WebClient wc = WebClient.create("http://localhost:8040/services/docservice/doc/out.pdf");
        DataHandler dh = wc.get(DataHandler.class);
        dh.writeTo(new FileOutputStream("out2.pdf"));
    }

}
