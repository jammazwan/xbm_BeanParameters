package jammazwan.xbm;

import org.apache.camel.Body;
import org.apache.camel.EndpointInject;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class BodyHeaderTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new HeaderBean()).to("mock:result");
			}
		};
	}

	public class HeaderBean {
		public String myMethod(@Body String body, @Header("ExtraInfo") String myvar) {
			return "Fred and " + myvar + " " + body;
		}
	}

	/*
	 * Above is the essence of this test.
	 * 
	 * Below is necessary infrastructure.
	 */

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("Fred and Mary married today");
		template.requestBodyAndHeader("direct:assert", "married today", "ExtraInfo", "Mary");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
