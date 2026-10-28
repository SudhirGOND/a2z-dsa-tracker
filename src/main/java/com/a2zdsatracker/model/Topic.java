package com.a2zdsatracker.model;

public enum Topic {
    BASICS("Learn the Basics", 54),
    SORTING("Sorting Techniques", 7),
    ARRAYS("Arrays", 40),
    BINARY_SEARCH("Binary Search", 32),
    STRINGS_BASIC("Strings (Basic)", 15),
    LINKED_LIST("LinkedList", 31),
    RECURSION("Recursion", 25),
    BIT_MANIPULATION("Bit Manipulation", 18),
    STACK_QUEUE("Stack & Queues", 30),
    SLIDING_WINDOW("Sliding Window & Two Pointer", 12),
    HEAPS("Heaps", 17),
    GREEDY("Greedy", 15),
    BINARY_TREES("Binary Trees", 38),
    BST("Binary Search Trees", 16),
    GRAPHS("Graphs", 53),
    DP("Dynamic Programming", 55),
    TRIES("Tries", 7),
    STRINGS_ADVANCED("Strings (Advanced)", 9);

    private final String displayName;
    private final int totalProblems;

    Topic(String displayName, int totalProblems) {
        this.displayName = displayName;
        this.totalProblems = totalProblems;
    }

    public String getDisplayName() { return displayName; }
    public int getTotalProblems() { return totalProblems; }
}
