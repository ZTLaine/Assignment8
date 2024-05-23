package test;

import com.coderscampus.assignment.Assignment8;
import com.coderscampus.assignment.DataService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataServiceTest {

    @Test
    public void testGetData() {
        Assignment8 assignment8 = new Assignment8();

        for (int i = 0; i < 100; i++) {
            List<Integer> numbersList = assignment8.getNumbers();
        }
    }

    @Test
    public void testReadAllData(){

    }

//    @Test
//    public void readBlockFromList() {
//        DataService dataService = new DataService();
//        List<Integer> numbers;
//        Integer[] testNumbers = new Integer[1000];
//
//        numbers = dataService.getAss8().getNumbers();
//        testNumbers[0] = 1;
//        testNumbers[999] = 7;
//
//        assertEquals(numbers.get(0), testNumbers[0]);
//        assertEquals(numbers.get(999), testNumbers[999]);
//    }
//
//    @Test
//    public void readThreadsWorking() {
//        DataService dataService = new DataService();
//        List<Integer> numbers;
//        Integer[] testNumbers = new Integer[1000];
//
//        numbers = dataService.getAss8().getNumbers();
//        testNumbers[0] = 1;
//        testNumbers[999] = 7;
//
//        assertEquals(numbers.get(0), testNumbers[0]);
//        assertEquals(numbers.get(999), testNumbers[999]);
//    }
}
