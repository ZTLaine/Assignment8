package com.coderscampus.assignment;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDto {
    private List<Integer> inputNumbers;
    private Integer MaxNum;
    private AtomicInteger countedNum = new AtomicInteger(0);
}
