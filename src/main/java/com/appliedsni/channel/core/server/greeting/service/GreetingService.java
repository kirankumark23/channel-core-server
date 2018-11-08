package com.appliedsni.channel.core.server.greeting.service;



import org.springframework.stereotype.Component;

/**
 * Service class that provides operations for greetings.
 *
 * @author gauri
 */
@Component
public class GreetingService {

    /**
     * Get a public greeting.
     *
     * @return
     */
    public String getPublicGreeting() {
        return "Hello from the other side!";
    }

    /**
     * Get a greeting for a user.
     *
     * @return
     */
    public String getGreetingForUser(String username) {
        return String.format("Hello %s!", username);
    }
}