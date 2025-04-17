package main.table;

import main.model.Course;

import java.util.List;

/**
 * Defines a common interface for storing {@link Course} objects.
 * <p>
 * Supports inserting, searching, and retrieving all stored courses.
 *
 * @author John Kirven
 */
public interface CourseTable {

    /**
     * Inserts a course into the table.
     * @param course the course to insert
     */
    void insert(Course course);

    /**
     * Searches the table for a course with the given course name (e.g., "MATH201").
     *
     * @param courseName the full course identifier to search for
     * @return the matching {@link Course}, or {@code null} if not found
     */
    Course search(String courseName);

    /**
     * Returns a flattened list of all {@link Course} objects currently stored in the table.
     *
     * @return a list of all courses in the table
     */
    List<Course> getAllCourses();

}
