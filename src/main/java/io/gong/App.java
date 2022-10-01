package io.gong;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * This is the App entry point
 */
public class App {

    public static void main(String[] args) throws Exception {

        List<String> names = Arrays.asList("Alice", "Jack", "Bob");
        Duration duration = Duration.ofMinutes(60);

        List<LocalTime> localTimes = Logic.findAvailableSlots(names, duration);
        if (localTimes.isEmpty()) {
            System.out.println("There are no available slots" );
        } else {
            for (LocalTime localTime : localTimes){
                System.out.println("Available slot: " + localTime);
            }
        }
    }
}
