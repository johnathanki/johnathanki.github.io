package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a university course with a name, prefix, level, and prerequisites.
 * <p>
 * A course is identified by a combination of its {@code coursePrefix} (e.g., "CSCI")
 * and {@code courseLevel} (e.g., 101). It may also contain a list of course codes
 * representing its prerequisites.
 *
 * @author John Kirven
 */
public class Course {
    /** A 4-letter department or subject prefix, e.g. "MATH" or "CSCI". */
    public String coursePrefix;

    /** The full course name, e.g. "Discrete Mathematics". */
    public String courseName;

    /** The 3-digit course level, e.g. 101 or 200 */
    public int courseLevel;

    /** A list of course prerequisites in prefix + level format, e.g. "CSCI200" */
    public List<String> prerequisites = new ArrayList<>();

    /**
     * Constructs a new Course with no prefix, name, or prerequisites. The {@code courseLevel} defaults to -1.
     */
    public Course() {
        courseLevel = -1;
    }

    /**
     * Prints course information and prerequisites (if any) to the console.
     */
    public void printCourseInfo() {
        System.out.println("Course Information:");
        System.out.println(coursePrefix + courseLevel + " - " + courseName);
        if (!prerequisites.isEmpty()) {
            System.out.print("Prerequisites: ");
            for (int i = 0; i < prerequisites.size() - 1; i++) {
                System.out.print(prerequisites.get(i) + ", ");
            }
            System.out.println(prerequisites.getLast());
        }
    }


}
