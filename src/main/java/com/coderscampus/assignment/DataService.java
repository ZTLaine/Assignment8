package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DataService {
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
    private ExecutorService fixedTask = Executors.newFixedThreadPool(6);
    private AtomicInteger completedThreads = new AtomicInteger(0);
    private List<CompletableFuture<TaskDto>> futures = new ArrayList<>();
    private Map<Object, Long> numberAppearances = new HashMap<>();
    private Assignment8 ass8 = new Assignment8();
    private final Integer ITERATIONS = 1000;

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

    private void dataCount(){
        for (CompletableFuture<TaskDto> future : futures) {
            future.thenAccept(taskDto -> {
                List<Integer> inputNumbers = taskDto.getInputNumbers();
                numberAppearances.putAll(inputNumbers.stream()
                        .collect(Collectors.groupingBy(e -> e, Collectors.counting())));
            });
//            TaskDto taskDto = future.join();
//            numberAppearances = taskDto.getInputNumbers()
//                    .stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        }
    }

    public void analyze() {
        collectData();
        dataCount();
        System.out.println(numberAppearances);

        cachedTask.shutdown();
        fixedTask.shutdown();

//        Map<Thread, StackTraceElement[]> stackTraces = Thread.getAllStackTraces();
//        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet()) {
//            Thread thread = entry.getKey();
//            if (!thread.isDaemon()) {
//                System.out.println("Non-daemon thread: " + thread.getName());
//                for (StackTraceElement stackTraceElement : entry.getValue()) {
//                    System.out.println("\t" + stackTraceElement);
//                }
//            }
//        }

    }
}
