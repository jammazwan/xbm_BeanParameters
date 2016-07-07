package jammazwan.xbm;

import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class HeadersTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new HeaderBean()).to("mock:result");
			}
		};
	}

	public class HeaderBean {
		public String myMethod(@Headers Map<String, Object> headers) {
			return "Fred and " + headers.get("ExtraInfo");
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
		template.requestBodyAndHeader("direct:assert", "nothing that matters here", "ExtraInfo", "Mary");
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

}
