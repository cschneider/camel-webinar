package foo;

import org.apache.camel.ProducerTemplate;

public class CustomerSenderImpl implements CustomerSender {
	private ProducerTemplate producer;
	private String endpointUri;

	public CustomerSenderImpl(ProducerTemplate producer, String endpointUri) {
		this.producer = producer;
		this.endpointUri = endpointUri;
	}

	@Override
	public void send(Customer customer) {
		producer.sendBody(endpointUri, customer);
	}

}
