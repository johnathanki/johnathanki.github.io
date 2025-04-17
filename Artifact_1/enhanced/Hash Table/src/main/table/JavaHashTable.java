package main.table;

import main.model.Course;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * A custom Java HashTable implementation for storing {@link main.model.Course} objects.
 *
 * @author John Kirven
 */
public class JavaHashTable implements CourseTable {

    public Hashtable<Integer, LinkedList<Course>> table = new Hashtable<>();

    /**
     * Extracts a key based on teh first two digits of the course's level. Typically used to determine the appropriate
     * hash bucket index.
     *
     * @param course the Course object to extract the key from
     * @return an integer representing the hash key
     * @throws IllegalArgumentException if the course level is negative
     */
    public int getCourseKey(Course course) {
        if (course.courseLevel < 0) {
            throw new IllegalArgumentException("Key cannot be negative.");
        }
        return Integer.parseInt(String.valueOf(course.courseLevel).substring(0, 2));
    }

    @Override
    public void insert(Course course) {
        int key = getCourseKey(course);
        table.putIfAbsent(key, new LinkedList<>());
        table.get(key).add(course);
    }

    @Override
    public Course search(String courseName) {
        if (courseName.length() != 7) {
            System.err.println("You have entered an invalid course. Courses are 4 letters followed by 3 numbers.");
            return null;
        }

        String coursePrefix = courseName.substring(0, 4);
        int courseLevel;
        try {
            courseLevel = Integer.parseInt(courseName.substring(4));
        } catch (NumberFormatException e) {
            System.err.println("Invalid course level format.");
            return null;
        }

        int key = Integer.parseInt(courseName.substring(4, 6)); // two-digit prefix

        LinkedList<Course> bucket = table.get(key);
        if (bucket == null) {
            return null;
        }

        for (Course course : bucket) {
            if (course.courseLevel == courseLevel && course.coursePrefix.equalsIgnoreCase(coursePrefix)) {
                return course;
            }
        }

        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        // Go through each bucket
        for (LinkedList<Course> bucket : table.values()) {
            allCourses.addAll(bucket);
        }
        return allCourses;
    }
}
