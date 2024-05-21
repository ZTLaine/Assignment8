package com.coderscampus.assignment;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataService {
    private Assignment8 ass8 = new Assignment8();
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
    private ExecutorService fixedTask = Executors.newFixedThreadPool(6);
    private LinkedHashMap<Integer, Integer> numberAppearances = new LinkedHashMap<>();
    private TaskDto taskDto;

    public Assignment8 getAss8() {
        return ass8;
    }

    public void setAss8(Assignment8 ass8) {
        this.ass8 = ass8;
    }

    public void collectData() {
        for (int i = 0; i < 10; i++) {
            taskDto.setInputNumbers(CompletableFuture.supplyAsync(TaskDto::new, cachedTask)
                    .thenApply(TaskDto::getInputNumbers)
                    .thenAccept(TaskDto::fetchInputNumbers));
        }
    }

    private void dataCount() {

    }

    public void analyze() {

    }
}
