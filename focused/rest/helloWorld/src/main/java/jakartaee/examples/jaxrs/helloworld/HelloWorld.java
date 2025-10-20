package jakartaee.examples.jaxrs.helloworld;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Simple implementation of the HelloWorld MXBean and registrar.
 */
@ApplicationScoped
public class HelloWorld implements HelloWorldMXBean {

    private volatile String greeting = "Hello from JMX";

    @Override
    public String getGreeting() {
        return greeting;
    }

    @Override
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void registerMBean(@Observes AfterDeploymentValidation adv) {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName name = new ObjectName("jakartaee.examples.jaxrs.helloworld:type=HelloWorld");
            if (!mbs.isRegistered(name)) {
                mbs.registerMBean(this, name);
            }
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException | javax.management.NotCompliantMBeanException e) {
            // log or ignore - keep startup resilient
            e.printStackTrace();
        }
    }
}
