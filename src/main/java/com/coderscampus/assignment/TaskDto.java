package com.coderscampus.assignment;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDto {
    private List<Integer> inputNumbers;
    private Integer MaxNum;
    private AtomicInteger countedNum = new AtomicInteger(0);
    private Assignment8 ass8;

    public TaskDto() {
        ass8 = new Assignment8();
    }

    public List<Integer> getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers() {
        this.inputNumbers = inputNumbers;
    }

    public Integer getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(Integer maxNum) {
        MaxNum = maxNum;
    }

    public List<Integer> fetchInputNumbers() {
        inputNumbers.addAll(ass8.getNumbers());
        return this;
    }
}
