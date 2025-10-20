package jakartaee.examples.jaxrs.helloworld;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import jakarta.enterprise.context.ApplicationScoped;

/** Simple liveness check for the HelloWorld example. */
@Liveness
@ApplicationScoped
public class HelloWorldHealthCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.named("hello-world").up().build();
	}
}

