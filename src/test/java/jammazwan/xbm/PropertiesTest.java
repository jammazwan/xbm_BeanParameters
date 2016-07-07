package jammazwan.xbm;

import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Properties;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class PropertiesTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").process(new MyProcessor("Mary")).bean(new PropertiesBean()).to("mock:result");
			}
		};
	}

	public class PropertiesBean {
		public String myMethod(String body, @Properties Map<String, Object> properties) {
			return "Fred and " + body + " with property " + properties.get("foo");
		}
	}

	/*
	 * Above is the essence of this test.
	 * 
	 * Below is necessary infrastructure.
	 */

	public class MyProcessor implements Processor {
		private String name;

		public MyProcessor(String name) {
			this.name = name;
		}

		@Override
		public void process(Exchange exchange) throws Exception {
			exchange.setProperty("foo", "bar");
			exchange.getIn().setBody(name);
		}

	}

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("Fred and Mary with property bar");
		template.requestBody("direct:assert", "");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
