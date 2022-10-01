package io.gong;

import org.junit.Test;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class logicTest {
    @Test
    public void testFindAvailableSlots_happyFlow() throws Exception {
        List<String> names = Arrays.asList("Alice", "Jack", "bob");
        Duration duration = Duration.ofMinutes(60);

        List<LocalTime> localTimes = Logic.findAvailableSlots(names, duration);
        assertEquals(LocalTime.of(7, 0), localTimes.get(0));
        assertEquals(LocalTime.of(11, 30), localTimes.get(1));
        assertEquals(LocalTime.of(15, 0), localTimes.get(2));
        assertEquals(LocalTime.of(17, 0), localTimes.get(3));
    }

    @Test
    public void testFindAvailableSlots_happyFlow2() throws Exception {
        List<String> names = Arrays.asList("Alice", "jack");
        Duration duration = Duration.ofMinutes(60);

        List<LocalTime> localTimes = Logic.findAvailableSlots(names, duration);
        assertEquals(LocalTime.of(7, 0), localTimes.get(0));
        assertEquals(LocalTime.of(9, 30), localTimes.get(1));
        assertEquals(LocalTime.of(10, 30), localTimes.get(2));
        assertEquals(LocalTime.of(11, 30), localTimes.get(3));
        assertEquals(LocalTime.of(14, 0), localTimes.get(4));
        assertEquals(LocalTime.of(15, 0), localTimes.get(5));
        assertEquals(LocalTime.of(17, 0), localTimes.get(6));
    }

    @Test
    public void testFindAvailableSlots_happyFlow3() throws Exception {
        List<String> names = Arrays.asList("Alice", "jack", "bob");
        Duration duration = Duration.ofMinutes(30);

        List<LocalTime> localTimes = Logic.findAvailableSlots(names, duration);
        assertEquals(LocalTime.of(7, 0), localTimes.get(0));
        assertEquals(LocalTime.of(7, 30), localTimes.get(1));
        assertEquals(LocalTime.of(11, 30), localTimes.get(2));
        assertEquals(LocalTime.of(12, 0), localTimes.get(3));
        assertEquals(LocalTime.of(12, 30), localTimes.get(4));
        assertEquals(LocalTime.of(15, 0), localTimes.get(5));
        assertEquals(LocalTime.of(15, 30), localTimes.get(6));
        assertEquals(LocalTime.of(17, 0), localTimes.get(7));
        assertEquals(LocalTime.of(17, 30), localTimes.get(8));
        assertEquals(LocalTime.of(18, 00), localTimes.get(9));
    }

    @Test
    public void testFindAvailableSlots_personDoesNotExist(){
        try {
            List<String> names = Arrays.asList("Alice", "Jack", "ddd");
            Duration duration = Duration.ofMinutes(60);
            Logic.findAvailableSlots(names, duration);
            fail("expected exception was not occurred.");
        } catch (Exception e) {
            //if execution reaches here,
            //it indicates this exception was occurred.
            //so we need not handle it.
        }
    }

}