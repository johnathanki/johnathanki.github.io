package main.manager;

import main.model.Course;
import main.table.CourseTable;
import main.table.CustomHashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Manages interactions with a {@link CourseTable} implementation.
 * <p>
 * This class acts as a controller for inserting, searching, validating, and printing {@link Course} objects.
 *
 * @author John Kirven
 */
public class HashTableManager {

    public CourseTable courseTable;

    /**
     * Constructs a HashTableManager using a default {@link CustomHashTable}.
     */
    public HashTableManager() {
        this.courseTable = new CustomHashTable();
    }

    /**
     * Constructs a HashTableManager using the provided {@link CourseTable} implementation.
     * @param courseTable the course table implementation to use
     */
    public HashTableManager(CourseTable courseTable) {
        this.courseTable = courseTable;
    }

    /**
     * Loads data into {@link Course} objects and inserts them into a {@link CourseTable}.
     */
    public void loadCourseData() {
        try {
            String csvPath = "Data/Generated_Courses.csv";
            BufferedReader inputFile = new BufferedReader(new FileReader(csvPath));
            String lineData;
            // Read one line at a time and parse into Course
            while ((lineData = inputFile.readLine()) != null) {
                Course course = new Course();
                List<String> data = Arrays.asList(lineData.split(","));

                course.coursePrefix = data.get(0).substring(0,4);
                course.courseLevel = Integer.parseInt(data.get(0).substring(4));
                course.courseName = data.get(1);

                if (data.size() >= 3) { // Course has prerequisites
                    course.prerequisites.addAll(data.subList(2, data.size()));
                }
                insert(course);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks to see if prerequisites present in the {@link CourseTable} link to valid {@link Course} objects.
     * @return true if all Courses are valid, otherwise false
     */
    public boolean validatePrereqs() {
        for (Course course : courseTable.getAllCourses()) {
            if (course.prerequisites == null || course.prerequisites.isEmpty()) {
                continue;
            }
            for (String prereqCode : course.prerequisites) {
                Course prereq = search(prereqCode);
                if (prereq == null || prereq.courseName == null) {
                    System.err.println("Invalid prerequisite found: " + prereqCode);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Print every course in the {@link CourseTable}
     */
    public void printCourseList() {
        for (Course course : courseTable.getAllCourses()) {
            System.out.println(course.coursePrefix + course.courseLevel + " - " + course.courseName);
        }
    }

    /**
     * Insert a {@link Course} object into the {@link CourseTable}.
     * @param course the course to be inserted
     */
    public void insert(Course course) {
        courseTable.insert(course);
    }

    /**
     * Search for a {@link Course} object whose identifiers match the search criteria.
     * @param courseName the course prefix and course level to search for
     * @return a {@link Course} object that matches the search criteria, otherwise {@code null}
     */
    public Course search(String courseName) {
        return courseTable.search(courseName);
    }
}
