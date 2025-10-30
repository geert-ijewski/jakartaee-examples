package jakartaee.examples.jaxrs.helloworld;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.annotation.PostConstruct;

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
    private volatile long lastHeartbeat = System.currentTimeMillis();

    @Override
    public String getGreeting() {
        return greeting;
    }

    @Override
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public void heartbeat() {
        lastHeartbeat = System.currentTimeMillis();
    }

    @Override
    public long getLastHeartbeat() {
        return lastHeartbeat;
    }


    @Override
    public boolean isAlive() {
        long now = System.currentTimeMillis();
        // Alive if heartbeat within last 2 minutes (120000 ms)
        return (now - lastHeartbeat) < 120_000L;
    }

    public void registerMBean(@Observes AfterDeploymentValidation adv) throws MalformedObjectNameException,
        InstanceAlreadyExistsException, MBeanRegistrationException, javax.management.NotCompliantMBeanException
    {
        System.err.println("Creating mbean");

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("jakartaee.examples.jaxrs.helloworld:type=Monitoring");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(this, name);
        }
    }

    /*
    @PostConstruct
    private void postConstructRegister() {
        try {
            // defensive: try to register if not already registered
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("jakartaee.examples.jaxrs.helloworld:type=HelloWorld");
            if (!mbs.isRegistered(name)) {
                mbs.registerMBean(this, name);
            }
        } catch (Throwable t) {
            // don't break startup if JMX registration fails
            System.err.println("Failed to register HelloWorld MBean in @PostConstruct: " + t.getMessage());
        }
    }
        */
}
