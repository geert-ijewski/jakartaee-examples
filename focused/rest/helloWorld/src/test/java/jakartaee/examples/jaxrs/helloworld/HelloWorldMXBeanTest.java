package jakartaee.examples.jaxrs.helloworld;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for HelloWorld MXBean implementation.
 */
public class HelloWorldMXBeanTest {

    @Test
    public void testGreetingGetterSetter() {
        HelloWorld hw = new HelloWorld();
        // default greeting set in implementation
        String defaultGreeting = hw.getGreeting();
        hw.setGreeting("Hi test");
        assertEquals("Hi test", hw.getGreeting());
        // restore
        hw.setGreeting(defaultGreeting);
    }
}
