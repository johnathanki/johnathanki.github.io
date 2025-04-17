package main.util;

import main.model.Course;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Utility class used to generate dummy course information for debugging and benchmarking.
 *
 * @author John Kirven
 */
public class CourseGenerator {

    public static final String[][] COURSE_PREFIXES = {
            {"CSCI", "Computer Science"},
            {"MATH", "Mathematics"},
            {"PHYS", "Physics"},
            {"CHEM", "Chemistry"},
            {"BIOL", "Biology"},
            {"ENGL", "English"},
            {"HIST", "History"},
            {"SOCI", "Sociology"},
            {"PSYC", "Psychology"},
            {"ECON", "Economics"},
            {"PHIL", "Philosophy"},
            {"ARTS", "Art"},
            {"MUSC", "Music"},
            {"LITR", "Literature"},
            {"ASTR", "Astronomy"},
            {"GEOL", "Geology"},
            {"STAT", "Statistics"},
            {"ENGR", "Engineering"},
            {"BUSI", "Business"},
            {"ACCT", "Accounting"},
            {"MGMT", "Management"},
            {"MARK", "Marketing"},
            {"FINC", "Finance"},
            {"INFO", "Information Systems"},
            {"CYBR", "Cybersecurity"},
            {"DATA", "Data Science"},
    };

    private static final int[] COURSE_LEVELS = {100, 101, 102, 150, 200, 201, 202, 240, 250, 255, 300, 305, 320, 330, 360,
            370, 380, 400, 450, 465};


    /**
     * Generate a {@link List} of {@link Course} objects for all course levels 100-499 for each of the course prefixes
     * present in the {@code COURSE_PREFIXES}
     *
     * @return a {@link List} of generated courses
     */
    public static List<Course> generateDummyCourses() {
        List<Course> courses = new LinkedList<>();
        for (String[] coursePrefix : COURSE_PREFIXES) {
            for (int j = 100; j < 499; j++) {
                Course c = new Course();
                c.coursePrefix = coursePrefix[0];
                c.courseLevel = j;
                c.courseName = coursePrefix[1] + j;
                courses.add(c);
            }
        }
        return courses;
    }

    /**
     * Generate dummy courses using all combinations of {@code COURSE_PREFIXES} and {@code COURSE_LEVELS} with
     * randomly selected prerequisites and write them to a CSV file for debugging.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        List<String> allCourses = new ArrayList<>();
        List<String> generatedOutput = new ArrayList<>();

        Random rand = new Random();

        for (String[] prefixPair : COURSE_PREFIXES) {
            String prefix = prefixPair[0];
            String subject = prefixPair[1];

            for (int level : COURSE_LEVELS) {
                String courseCode = prefix + level;
                String courseName = subject + " level " + courseCode;

                StringBuilder line = new StringBuilder();
                line.append(courseCode).append(",").append(courseName);

                // Only assign prereqs if course level is > 200
                if (level > 200) {
                    int numPrereqs = rand.nextInt(3); // 0 to 2 prereqs
                    Set<String> prereqs = new LinkedHashSet<>();
                    while (prereqs.size() < numPrereqs) {
                        String randomPrereq = allCourses.get(rand.nextInt(allCourses.size()));
                        if (!randomPrereq.equals(courseCode)) {
                            prereqs.add(randomPrereq);
                        }
                    }

                    // Seperate each prereq with commas
                    for (String prereq : prereqs) {
                        line.append(",").append(prereq);
                    }
                }

                // Add to global course list now, so it can't become its own prereq
                allCourses.add(courseCode);

                generatedOutput.add(line.toString());
            }
        }

        // Write to file
        try (FileWriter writer = new FileWriter("Generated_Courses.csv")) {
            for (String line : generatedOutput) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
