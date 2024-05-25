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
    private List<Integer> allInputs = new ArrayList<>();
    private Map<Object, Long> numberAppearances = new HashMap<>();
    private Assignment8 ass8 = new Assignment8();
    private Integer iterations = 1000;

    public DataService() {
    }

    public DataService(Integer iterations) {
        this.iterations = iterations;
    }

    public ExecutorService getCachedTask() {
        return cachedTask;
    }

    public void setCachedTask(ExecutorService cachedTask) {
        this.cachedTask = cachedTask;
    }

    public ExecutorService getFixedTask() {
        return fixedTask;
    }

    public void setFixedTask(ExecutorService fixedTask) {
        this.fixedTask = fixedTask;
    }

    public AtomicInteger getCompletedThreads() {
        return completedThreads;
    }

    public void setCompletedThreads(AtomicInteger completedThreads) {
        this.completedThreads = completedThreads;
    }

    public List<CompletableFuture<TaskDto>> getFutures() {
        return futures;
    }

    public void setFutures(List<CompletableFuture<TaskDto>> futures) {
        this.futures = futures;
    }

    public Map<Object, Long> getNumberAppearances() {
        return numberAppearances;
    }

    public void setNumberAppearances(Map<Object, Long> numberAppearances) {
        this.numberAppearances = numberAppearances;
    }

    public Assignment8 getAss8() {
        return ass8;
    }

    public void setAss8(Assignment8 ass8) {
        this.ass8 = ass8;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public List<Integer> getAllInputs() {
        return allInputs;
    }

    public void setAllInputs(List<Integer> allInputs) {
        this.allInputs = allInputs;
    }

    private void collectData() {
        for (int i = 0; i < iterations; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> new TaskDto(ass8), cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers, cachedTask));
            incrementCompletedThreads();
//            System.out.println("Started " + completedThreads.get());
        }
        while (futures.stream().filter(CompletableFuture::isDone).count() < iterations) {
//            System.out.println("Completed collection threads: " + futures.stream().filter(CompletableFuture::isDone).count());
        }
//        System.out.println("Completed collection threads: " + futures.stream().filter(CompletableFuture::isDone).count());
    }


    private void incrementCompletedThreads() {
        synchronized (completedThreads) {
            completedThreads.incrementAndGet();
        }
    }

    private void dataCount() {
        for (CompletableFuture<TaskDto> future : futures) {
            future.thenAccept(taskDto -> {
                List<Integer> inputNumbers = taskDto.getInputNumbers();
                Map<Object, Long> instanceNumAppearances = inputNumbers.stream()
                        .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                if (numberAppearances.entrySet().isEmpty()) {
                    numberAppearances = instanceNumAppearances;
                } else {
                    numberAppearances = instanceNumAppearances.entrySet()
                            .stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() + numberAppearances.get(entry.getKey())));
                }
                //allInputs is just to make testing less annoying
                allInputs.addAll(inputNumbers);
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
