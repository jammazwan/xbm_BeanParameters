package jammazwan.xbm;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class StringBodyTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new StringBodyBean()).to("mock:result");
			}
		};
	}

	public class StringBodyBean {
		public String myMethod(String body) {
			return "Fred and " + body;
		}
	}

	/*
	 * Above is the essence of this test.
	 * 
	 * Below is necessary infrastructure.
	 */

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("Fred and Mary");
		template.requestBody("direct:assert", "Mary");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
