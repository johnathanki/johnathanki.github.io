//============================================================================
// Name        : HashTable.cpp
// Author      : John Kirven
// Version     : 1.0
// Description : Hash Table structure for holding Course information
//============================================================================


#include <iostream>

#include "HashTable.h"

using namespace std;

/**
 * Default constructor
 */
HashTable::HashTable() {
    // Initialize nodes with default size
    nodes.resize(tableSize);
    nodes.empty(); // Ensure the nodes vector is empty
    for (int i = 0; i < tableSize; i++) { // Loop through all nodes in the bucket and initialize
        // them with an empty Node
        Node emptyNode = Node();
        nodes.at(i).push_back(emptyNode); // Put the empty node into the bucket
    }
}

/**
 * Constructor for specifying size of the table
 * Use to improve efficiency of hashing algorithm
 * by reducing collisions without wasting memory.
 */
HashTable::HashTable(unsigned int size) {
    // Initialize nodes with the specified size
    this->tableSize = size;
    nodes.resize(tableSize);
    nodes.empty(); // Ensure the nodes vector is empty
    for (int i = 0; i < tableSize; i++) { // Loop through all nodes in the bucket and initialize
        // them with an empty Node
        Node emptyNode = Node();
        nodes.at(i).push_back(emptyNode); // Put the empty node in the bucket
    }
}


/**
 * Destructor
 */
HashTable::~HashTable() {
    // erase nodes beginning
    nodes.erase(nodes.begin());
}

/**
 * Calculate the hash value of a given key.
 * Note that key is specifically defined as
 * unsigned int to prevent undefined results
 * of a negative list index.
 *
 * @param key The key to hash
 * @return The calculated hash
 */
unsigned int HashTable::Hash(int key) {
    // hash is equal to key mod tableSize
    return key % tableSize;
}

/**
 * Insert a course
 *
 * @param course The course to be inserted
 */
void HashTable::Insert(Course course) {
    // find the key for the course using the first two digits of the courseLevel
    unsigned key = Hash(atoi(to_string(course.courseLevel).substr(0, 2).c_str()));

    Node node = nodes.at(key).at(0); // Fetch the first node in vector of nodes - nodes[key][0]

    if (node.key == UINT_MAX) { // Default key so no data held here
        nodes.at(key)[0] = Node(course, key); // Set the first node for this key to new node with course and key
    }
    else { // else add to next position
        nodes.at(key).push_back(Node(course, key));
    }

}

/**
 * Search for the specified course
 *
 * @param course The course to search for
 */
Course HashTable::Search(string courseName) {
    Course course;
    if (courseName.length() != 7) {
        cout << "You have entered an invalid course, courses are 4 letters followed by 3 numbers." << endl;
        return course;
    }
    // Get courseLevel from courseName
    int courseLevel = stoi(courseName.substr(4));

    // find the key for the course using the first two digits of the courseLevel
    unsigned key = Hash(stoi(courseName.substr(4, 2)));

    // Start at the beginning of the bucket
    Node node = nodes.at(key).at(0);

    if (node.key == UINT_MAX) { // Empty node
        return course; // Return empty course
    }
    for (Node nodes : nodes.at(key)) { // Loop through every node in this bucket
        if (nodes.course.courseLevel == courseLevel) { // Match found
            return nodes.course; // Return this nodes course
        }
    }
    // Return empty course
    cout << "Course not found: " << courseName;
    return course;
}
