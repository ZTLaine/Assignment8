//  5/26/24
//  Zack Laine
//  Assignment 8

package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;

public class TaskDto {
    private List<Integer> inputNumbers;
    private Assignment8 ass8;

    public TaskDto(Assignment8 ass8) {
        this.ass8 = ass8;
        inputNumbers = new ArrayList<Integer>();
    }

    public List<Integer> getInputNumbers() {
        return inputNumbers;
    }

    public TaskDto fetchInputNumbers() {
        inputNumbers.addAll(ass8.getNumbers());
        return this;
    }
}
