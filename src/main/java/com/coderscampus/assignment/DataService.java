package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataService {
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
    private ExecutorService fixedTask = Executors.newFixedThreadPool(6);
    private AtomicInteger completedThreads = new AtomicInteger(0);
    private List<CompletableFuture<TaskDto>> futures = new ArrayList<>();
    private Map<Integer, Long> numberAppearances = new HashMap<>();
    private Assignment8 ass8 = new Assignment8();
    private final Integer ITERATIONS = 150;

    public void collectData() {
        for (int i = 0; i < ITERATIONS; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> new TaskDto(ass8), cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers, cachedTask));
            incrementCompletedThreads();
            System.out.println("Started " + completedThreads.get());
        }
        while (futures.stream().filter(CompletableFuture::isDone).count() < ITERATIONS) {
            System.out.println("Completed collection threads: " + futures.stream().filter(CompletableFuture::isDone).count());
        }

        System.out.println("Completed collection threads: " + futures.stream().filter(CompletableFuture::isDone).count());
    }


    private void incrementCompletedThreads() {
        synchronized (completedThreads) {
            completedThreads.incrementAndGet();
        }
    }

    private void dataCount() {
        for (CompletableFuture<TaskDto> future : futures) {
            future.thenAcceptAsync(taskDto -> {
                numberAppearances = taskDto.getInputNumbers().parallelStream()
                        .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            }, fixedTask);

//        while (futures.stream().filter(CompletableFuture::isDone).count() < iterations) {
//            System.out.println("Completed counting threads: " + futures.stream().filter(CompletableFuture::isDone).count());
        }
    }

    public void analyze() {
        collectData();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        dataCount();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    }
}
