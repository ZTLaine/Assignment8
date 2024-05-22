package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DataService {
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
    //    private ExecutorService fixedTask = Executors.newFixedThreadPool(6);
    private AtomicInteger completedThreads = new AtomicInteger(0);
    private List<Integer> inputNumbers;
    private LinkedHashMap<Integer, Integer> numberAppearances = new LinkedHashMap<>();
//    private TaskDto taskDto = new TaskDto();

    public void collectData() {
        List<CompletableFuture<TaskDto>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
//            taskDto.setInputNumbers();
            futures.add(CompletableFuture.supplyAsync(TaskDto::new, cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers));
            incrementCompletedThreads();
            System.out.println("Completed " + completedThreads);
        }
    }

    private AtomicInteger incrementCompletedThreads() {
        synchronized (completedThreads) {
            completedThreads.incrementAndGet();
            return completedThreads;
        }
    }

    private void dataCount() {

    }

    public void analyze() {

    }
}
