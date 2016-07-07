package jammazwan.xbm;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class ObjectBodyTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() {
		return new RouteBuilder() {
			public void configure() {
				from("direct:assert").bean(new ObjectBodyBean()).to("mock:result");
			}
		};
	}

	public class ObjectBodyBean {
		public String myMethod(Object body) {
			Person person = (Person) body;
			return "Fred and " + person.getName();
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
		Person person = new Person();
		person.setName("Mary");
		template.requestBody("direct:assert", person);
		resultEndpoint.assertIsSatisfied();
	}

	@EndpointInject(uri = "mock:result")
	protected MockEndpoint resultEndpoint;

	public class Person {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
