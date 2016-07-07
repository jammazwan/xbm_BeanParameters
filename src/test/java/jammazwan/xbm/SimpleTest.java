package jammazwan.xbm;

import org.apache.camel.Body;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.language.Simple;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class SimpleTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new SimpleBean()).to("mock:result");
			}
		};
	}

	public class SimpleBean {
		public String myMethod(@Body String body, @Simple("${body} contains 'Fred'") boolean hasFred) {
			if(hasFred){
				return "Has Fred";
			} else {
				return "Needs Fred";
			}
		}
	}

	/*
	 * Above is the essence of this test.
	 * 
	 * Below is necessary infrastructure.
	 */

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("Needs Fred");
		template.requestBody("direct:assert", "Mary");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
