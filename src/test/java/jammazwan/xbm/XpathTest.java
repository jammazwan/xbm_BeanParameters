package jammazwan.xbm;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.language.XPath;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class XpathTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new XpathBean()).to("mock:result");
			}
		};
	}

	public class XpathBean {
		public String myMethod(@XPath("//status/text()") String status) {
			return "Fred is " + status;
		}
	}

	/*
	 * Above is the essence of this test.
	 * 
	 * Below is necessary infrastructure.
	 */

	@Test
	public void testSendMatchingMessage() throws Exception {
		resultEndpoint.expectedBodiesReceived("Fred is Occupied");
		template.requestBody("direct:assert", "<status>Occupied</status>");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
