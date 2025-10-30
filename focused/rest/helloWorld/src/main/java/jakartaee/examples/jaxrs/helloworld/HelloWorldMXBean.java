package jakartaee.examples.jaxrs.helloworld;

/**
 * MXBean interface to expose application health and simple metrics.
 */
public interface HelloWorldMXBean {
    String getGreeting();
    void setGreeting(String greeting);

    /**
     * Record a heartbeat (application alive signal).
     */
    void heartbeat();

    /**
     * Get the epoch milliseconds of the last heartbeat.
     */
    long getLastHeartbeat();

    /**
     * Check if the application is currently considered alive.
     * This simple check returns true if a heartbeat was recorded within the last 2 minutes.
     */
    boolean isAlive();
}
