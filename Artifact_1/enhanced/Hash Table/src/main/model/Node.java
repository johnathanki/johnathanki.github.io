package main.model;

/**
 * Represents a node within a hash table bucket.
 * <p>
 * Each node contains a {@link Course} and the associated hash key used to determine its bucket.
 *
 * @author John Kirven
 */
public class Node {
    public Course course;
    public int key;

    /**
     * Constructs a placeholder node with a default key of -1.
     */
    public Node() {
        key = -1;
    }

    /**
     * Constructs a node with the specified course and key.
     *
     * @param course the course to store
     * @param key the hash key associated with the course
     */
    public Node(Course course, int key) {
        this.course = course;
        this.key = key;
    }
}