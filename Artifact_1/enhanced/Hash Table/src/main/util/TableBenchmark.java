package main.util;

import main.manager.HashTableManager;
import main.model.Course;
import main.table.JavaHashTable;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Utility class that tests the average performance of the {@link main.table.CustomHashTable} and {@link JavaHashTable}
 * implementations. It currently tests insertion speed and search speed.
 *
 * @author John Kirven
 */
public class TableBenchmark {

    private static final int TOTAL_SEARCHES = 100000;
    private static final int TOTAL_POPULATIONS = 10000;
    private static final String SEARCH_TERM = "CSCI200";


    public static void main(String[] args) {
        List<Course> courseList = CourseGenerator.generateDummyCourses();
        System.out.println("Generated " + courseList.size() + " courses");

        System.out.println("-".repeat(40));
        System.out.println("Starting Hash Table Benchmarks");

        benchmarkInsertion(courseList);
        System.out.println("-".repeat(40));

        benchmarkSearch();
    }

    /**
     * Benchmarks the insertion timing of the {@link main.table.CustomHashTable} and {@link JavaHashTable}
     * implementations.
     * <p>
     * Repeats insertion based on {@code TOTAL_POPULATIONS} and prints the average time to the console.
     *
     * @param courseList the courses to be inserted into the tables
     */
    private static void benchmarkInsertion(List<Course> courseList) {
        long customInsertTotal = 0;
        long javaInsertTotal = 0;

        for (int i = 0; i < TOTAL_POPULATIONS; i++) {
            customInsertTotal += timeTablePopulation(new HashTableManager(), courseList);
            javaInsertTotal += timeTablePopulation(new HashTableManager(new JavaHashTable()), courseList);
        }

        long avgCustom = nanoToMicro(customInsertTotal / TOTAL_POPULATIONS);
        long avgJava = nanoToMicro(javaInsertTotal / TOTAL_POPULATIONS);

        System.out.println("Custom hash table population average: " + avgCustom + "μs.");
        System.out.println("Java HashTable population average: " + avgJava + "μs.");
    }

    /**
     * Benchmarks the search timing of the {@link main.table.CustomHashTable} and {@link JavaHashTable}
     * implementations.
     * <p>
     * Populates the tables once, warms up the JVM, and repeatedly searches based on {@code TOTAL_SEARCHES} and
     * prints the average time to the console.
     */
    private static void benchmarkSearch() {
        HashTableManager customTable = new HashTableManager();
        HashTableManager javaTable = new HashTableManager(new JavaHashTable());

        // Populate tables once before search benchmark
        List<Course> courseList = CourseGenerator.generateDummyCourses();
        courseList.forEach(customTable::insert);
        courseList.forEach(javaTable::insert);

        // Warm-up
        for (int i = 0; i < 1000; i++) {
            customTable.search(SEARCH_TERM);
            javaTable.search(SEARCH_TERM);
        }

        long customSearchTotal = 0;
        long javaSearchTotal = 0;

        for (int i = 0; i < TOTAL_SEARCHES; i++) {
            customSearchTotal += timeSearch(customTable);
            javaSearchTotal += timeSearch(javaTable);
        }

        System.out.println("Custom hash table search average: " + (customSearchTotal / TOTAL_SEARCHES) + "ns.");
        System.out.println("Java HashTable search average: " + (javaSearchTotal / TOTAL_SEARCHES) + "ns.");
    }

    /**
     * Inserts all courses into the table managed by the {@link HashTableManager} and calculates the time it takes in
     * nanoseconds.
     *
     * @param manager the HashTableManager to use for insertion
     * @param courses the list of courses to insert
     * @return the amount of time (in nanoseconds) it took to complete the insertions
     */
    private static long timeTablePopulation(HashTableManager manager, List<Course> courses) {
        long start = System.nanoTime();
        for (Course course : courses) {
            manager.insert(course);
        }
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Searches the course table managed by the {@link HashTableManager} and calculates the time it takes in
     * nanoseconds.
     *
     * @param manager the HashTableManager to use for searching
     * @return the amount of time (in nanoseconds) it took to complete the search
     */
    private static long timeSearch(HashTableManager manager) {
        long start = System.nanoTime();
        manager.search(TableBenchmark.SEARCH_TERM);
        long end = System.nanoTime();
        return end - start;
    }

    /**
     * Converts a duration from nanoseconds to microseconds.
     *
     * @param duration the duration in nanoseconds
     * @return the equivalent duration in microseconds
     */
    private static long nanoToMicro(long duration) {
        return TimeUnit.NANOSECONDS.toMicros(duration);
    }
}
