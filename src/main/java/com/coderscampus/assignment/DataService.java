//  5/26/24
//  Zack Laine
//  Assignment 8

package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DataService {
    private ExecutorService cachedTask = Executors.newCachedThreadPool();
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

    public Map<Object, Long> getNumberAppearances() {
        return numberAppearances;
    }

    public List<Integer> getAllInputs() {
        return allInputs;
    }

    private void collectData() {
        for (int i = 0; i < iterations; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> new TaskDto(ass8), cachedTask)
                    .thenApplyAsync(TaskDto::fetchInputNumbers, cachedTask));
        }
        while (futures.stream().filter(CompletableFuture::isDone).count() < iterations) {}
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
        }
    }

    public void analyze() {
        collectData();
        dataCount();
        System.out.println(numberAppearances);

        cachedTask.shutdown();
    }
}
