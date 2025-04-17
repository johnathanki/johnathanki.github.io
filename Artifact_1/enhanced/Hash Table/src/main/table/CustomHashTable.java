package main.table;

import main.model.Course;
import main.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom hash table implementation for storing {@link main.model.Course} objects.
 *
 * @author John Kirven
 */
public class CustomHashTable implements CourseTable {

    private final int tableSize;
    @SuppressWarnings("unchecked")
    private final ArrayList<Node>[] nodes;

    /**
     * Constructs a CustomHashTable with a specified number of buckets. Each bucket then has an empty {@link Node}
     * added as a placeholder.
     *
     * @param tableSize the number of buckets in the hash table
     */
    public CustomHashTable(int tableSize) {
        this.tableSize = tableSize;
        this.nodes = new ArrayList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            this.nodes[i] = new ArrayList<>();

            Node node = new Node();
            // Add a placeholder node into each bucket
            this.nodes[i].add(node);
        }
    }

    /**
     * Constructs a CustomHashTable with a specified number of buckets. Each bucket then has an empty {@link Node}
     * added as a placeholder.
     */
    public CustomHashTable() {
        this(100);
    }

    /**
     * Computes a hash bucket index for the given key.
     *
     * @param key non-negative integer key
     * @return the index of the corresponding bucket
     * @throws IllegalArgumentException if key is negative
     */
    public int hash(int key) {
        if (key < 0) {
            throw new IllegalArgumentException("Key cannot be negative.");
        }
        return key % tableSize;
    }

    @Override
    public void insert(Course course) {
        int key = hash(Integer.parseInt(String.valueOf(course.courseLevel).substring(0, 2)));

        Node node = nodes[key].getFirst();
        if (node.key == -1) { // Remove the placeholder node
            nodes[key].removeFirst();
            nodes[key].addFirst(new Node(course, key));
        } else {
            nodes[key].addLast(new Node(course, key));
        }
    }

    @Override
    public Course search(String courseName) {
        if (courseName.length() > 7) {
            System.err.println("You have entered an invalid course. Courses are 4 letters followed by 3 numbers.");
            return null;
        }

        String coursePrefix = courseName.substring(0, 4); // First 4 letters
        int courseLevel = Integer.parseInt(courseName.substring(4)); // Last 3 digits

        int key = hash(Integer.parseInt(courseName.substring(4,6)));

        Node node = nodes[key].getFirst();
        if (node.key == -1) { // Bucket only has the placeholder node
            return null;
        }
        // Search each node in the bucket
        for (Node nodes : nodes[key]) {
            if (nodes.course.courseLevel == courseLevel
                    && nodes.course.coursePrefix.equalsIgnoreCase(coursePrefix)) {
                return nodes.course;
            }
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        // Go through each bucket
        for (ArrayList<Node> bucket : nodes) {
            // Go through each node
            for (Node node : bucket) {
                if (node.key != -1 && node.course != null) {
                    allCourses.add(node.course);
                }
            }
        }
        return allCourses;
    }
}
