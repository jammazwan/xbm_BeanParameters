### To Run

I used the model of testing where route and test code is in one class, just to keep you from having to look multiple places for every different route. 

Run each test independently to observe the different parameters. As restated below:

### StringBodyTest

A single String parameter is consumed as a message body. Run StringBodyTest.

### ObjectBodyTest

A single Object parameter is consumed as a message body. Run ObjectBodyTest.

### HeaderTest

A single Header parameter is consumed as a parameter. Run HeaderTest.

### BodyHeaderTest

Both body and Header are both consumed as parameters. Run BodyHeaderTest.

### HeadersTest

Headers is consumed as a single parameter. Run HeadersTest.

### XpathTest

An Xpath statement and a body are consumed as parameters. Run XpathTest

Tip: I provided a pretty simple example here for easy understanding. If you want a more complex (realistic?) example try BeanWithXPathInjectionUsingHeaderValueTest in the camel core code.

### PropertiesTest

Properties are consumed as a parameter. Run PropertiesTest

### SimpleTest

A simple language expression is consumed as a parameter. Run SimpleTest

### BeanParameters NOTES:

##### Editorial comments:

Reading CamelInAction v1 it was really fun to watch Claus Isben get all excited about Camel beans, because beans don't know anything about Camel! Then you could write any code and it wouldn't have anything to do with Camel! And still run it as part of a Camel route. Way cool.

But I keep preferring the Processor because I am lazy, and just having the exchange around as an argument gives me access to everything my lazy self might need to process my data as I would wish. Headers, context, it's got it all! So now, I feel like some kind of dummy - because I know that Claus is 100 times smarter than me so how could I possibly disagree with him?

Then I partially redeemed my damaged self image when I made a presentation to a shop that was looking at adopting Camel, and I noted with great enthusiasm how terrific it was that they could bring in their legacy code and/or write bean wrappers for it, and presto-change-o they had great Camel functionality without writing Camel code! Yeah!

**_This project shows you how to consume beans with the parameters available in a processor_** by instead bringing into the bean world all the possible parameters that your bean might need to do it's job. Headers, other arguments, you name it.

_But wait!_ Doesn't that defeat the stated purpose of using a bean instead of a processor? Sure seems that way to me. From my perspective, why not just use a dogone processor? By bringing all this stuff in you're Camel centric anyway... Oh well. Maybe if we just use this functionality set, the opportunities that it allows for will become more evident. 