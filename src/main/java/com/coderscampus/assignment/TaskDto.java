package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDto {
    private List<Integer> inputNumbers;
    private Integer MaxNum;
//    private AtomicInteger countedNum = new AtomicInteger(0);
    private Assignment8 ass8;

    public TaskDto(Assignment8 ass8) {
        this.ass8 = ass8;
        //make sure the ass is coming from DataService and is not new
        inputNumbers = new ArrayList<Integer>();
    }

    public List<Integer> getInputNumbers() {
        return inputNumbers;
    }

//    public void setInputNumbers() {
//        this.inputNumbers = inputNumbers;
//    }

    public Integer getMaxNum() {
        return MaxNum;
    }

    public void setMaxNum(Integer maxNum) {
        MaxNum = maxNum;
    }

    public TaskDto fetchInputNumbers() {
        inputNumbers.addAll(ass8.getNumbers());
        return this;
    }
}
