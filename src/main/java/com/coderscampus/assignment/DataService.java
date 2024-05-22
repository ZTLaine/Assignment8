package com.coderscampus.assignment;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataService {
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
//    private ExecutorService fixedTask = Executors.newFixedThreadPool(6);
    private LinkedHashMap<Integer, Integer> numberAppearances = new LinkedHashMap<>();
//    private TaskDto taskDto = new TaskDto();

    public void collectData() {
        for (int i = 0; i < 10; i++) {
//            taskDto.setInputNumbers();
            CompletableFuture<TaskDto> taskDtoCompletableFuture = CompletableFuture.supplyAsync(TaskDto::new, cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers);
        }
    }

    private void dataCount() {

    }

    public void analyze() {

    }
}
