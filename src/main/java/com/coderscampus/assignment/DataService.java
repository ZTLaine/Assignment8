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
    private List<CompletableFuture<TaskDto>> futures = new ArrayList<>();
    private List<Integer> numbersRetrieved = new ArrayList<>();
    private LinkedHashMap<Integer, Integer> numberAppearances = new LinkedHashMap<>();
    private Assignment8 ass8 = new Assignment8();

    public void collectData() {
        for (int i = 0; i < 150; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> new TaskDto(ass8), cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers, cachedTask));
            incrementCompletedThreads();
            System.out.println("Completed " + completedThreads.get());
        }

        for (CompletableFuture<TaskDto> future : futures) {
            future.thenAccept(taskDto -> {
                for (Integer inputNum : taskDto.getInputNumbers()) {
                    numbersRetrieved.add(inputNum);
                }
            });
        }
//        TODO: Make sure it's actually getting the number of inputs correctly, and check if the above for block is async or not
        System.out.println(numbersRetrieved.size());
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
