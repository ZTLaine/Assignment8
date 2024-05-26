//  5/26/24
//  Zack Laine
//  Assignment 8

package test;

import com.coderscampus.assignment.Assignment8;
import com.coderscampus.assignment.DataService;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DataServiceTest {

    @Test
    public void read_all_data(){
        DataService dataService = new DataService();
        Assignment8 assignment8 = new Assignment8();

        dataService.analyze();

        assertEquals(dataService.getAllInputs().size(), assignment8.getNumbersTest().size());
    }

    @Test
    public void count_data(){
        DataService dataService = new DataService();
        Assignment8 assignment8 = new Assignment8();
        Map<Object, Long> testNumAppearances;
        Integer totalCount = 0;

        dataService.analyze();
        testNumAppearances = assignment8.getNumbersTest().stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        totalCount = dataService.getNumberAppearances().values()
                .stream()
                .mapToInt(Long::intValue)
                .sum();

        assertEquals(dataService.getNumberAppearances(),testNumAppearances);
        assertEquals(totalCount,(Integer) 1000000);
    }

    @Test
    public void avoid_hanging_threads() throws InterruptedException {
        DataService dataService = new DataService();
        Boolean hanging = false;

        dataService.analyze();
        Thread.sleep(1000);

        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
            Thread thread = entry.getKey();
            if (!thread.isDaemon() && !Objects.equals(thread.getName(), "main")) {
                System.out.println("Non-daemon thread: " + thread.getName());
                hanging = true;
//                for (StackTraceElement stackTraceElement : entry.getValue()) {
//                    System.out.println("\t" + stackTraceElement);
//                }
            }
        }

        assertEquals(hanging,false);
    }
}
