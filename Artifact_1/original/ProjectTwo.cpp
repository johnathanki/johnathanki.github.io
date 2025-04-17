//============================================================================
// Name        : ProjectTwo.cpp
// Author      : John Kirven
// Version     : 1.0
// Description : Final project parsing data and sorting into HashTable
//============================================================================

#include <fstream>
#include <iostream>
#include <sstream>
#include "HashTable.h"

using namespace std;

/**
 * Loads course data from the specified file into the specified hash table
 *
 * @param csvPath the path to the input file
 * @param hashTable the hash table used to store the data
 */
void loadCourseData(string csvPath, HashTable* hashTable) {
    // File stream
    fstream inputFile;
    // Open the file
    inputFile.open(csvPath, ios::in);

    // If file is open, continue and read file
    if (inputFile.is_open()) {
        // Placeholder for line data
        string lineData;

        // Read one line at a time
        while (getline(inputFile, lineData)) {
            // Placeholder for new course
            Course course = Course();

            // Vector to hold comma delimited line data
            vector<string> data;
            
            // String stream to delimit on commas
            stringstream ss(lineData);
            string temp;

            // Split line at commas
            while (getline(ss, temp, ',')) {
                data.push_back(temp);
            }
            // data structure:
            //data[0] = coursePrefix+level   data[1] = courseName   data[2...]prerequisites
            course.coursePrefix = data.at(0).substr(0, 4); // First 4 characters of data[0]

            course.courseLevel = stoi(data.at(0).substr(4)); // Last characters of data[0]

            course.courseName = data.at(1); // data[1]
            if (data.size() >= 3) { // If length >= 3 there are prereqs
                // Loop through each prereq
                for (int i = 2; i < data.size(); i++) {
                    course.prerequisites.push_back(data.at(i));
                }
            }
            // Data is sorted and course is properly initialized, add to hash table
            hashTable->Insert(course);

        }
    }
}

/**
 * Validates the prerequisite courses in the hash table
 *
 * @param hashTable the hash table containing the course list
 * @return true if the table contains all valid prereqs, otherwise false
 */
bool validatePrereqs(HashTable* hashTable) {
    Course course;
    for (int i = 0; i < hashTable->nodes.size(); i++) { // Loop through each bucket
        for (Node node : hashTable->nodes.at(i)) { // Loop through all values in that bucket
            if (node.key != UINT_MAX) { // Ignore nodes with default key
                if (node.course.prerequisites.size() != 0) { // Only check courses that have prereqs
                    for (string s : node.course.prerequisites) {
                        course = hashTable->Search(s);
                        if (course.courseName.empty()) {
                            cout << "Invalid prerequisite found: " << s << endl;
                            return false;
                        }
                    }
                }
            }
        }
    }
    return true;
}

/**
 * Print a list of all courses
 * 
 * @param hashTable the hash table containing the course list
 */
void printCourseList(HashTable* hashTable) {
    for (int i = 0; i < hashTable->nodes.size(); i++) { // Loop through each bucket
        for (Node node : hashTable->nodes.at(i)) { // Loop through all values in that bucket
            if (node.key != UINT_MAX) { // Ignore nodes with default key
                cout << node.course.coursePrefix << node.course.courseLevel << " - "
                    << node.course.courseName << endl;
            }
        }
    }
}

/**
 * Print course information for a given course
 *
 * @param course the course
 */
void printCourseInfo(Course course) {
    cout << "Course Information: " << endl; // Print header
    // Print course information
    cout << course.coursePrefix << course.courseLevel << " - " << course.courseName << endl;
    if (course.prerequisites.size() > 0) { // Check for prereqs
        cout << "Prerequisites: ";
        for (int i = 0; i < course.prerequisites.size() - 1; i++) {
            // Print all but last prereq followed by comma
            cout << course.prerequisites.at(i) << ", ";
        }
        // Print last prereq followed by new line
        cout << course.prerequisites.at(course.prerequisites.size() - 1) << endl;
    }
}

int main()
{
    // Default path to course list file
    string csvPath = "CourseList.csv";

    // Initialize the hash table
    HashTable* courseTable;
    Course course;
    courseTable = new HashTable();

    // Build menu
    int choice = 0;
    string name; // placeholder for search feature
    while (choice != 9) {
        cout << "Menu:" << endl;
        cout << "1. Load course information." << endl;
        cout << "2. Print course list." << endl;
        cout << "3. Search for course." << endl;
        cout << "9. Exit program." << endl;

        cin >> choice;
        switch (choice) {
        case 1: // Load data
            loadCourseData(csvPath, courseTable);
            validatePrereqs(courseTable);
            break;
        case 2: // Print data
            printCourseList(courseTable);
            break;
        case 3: // Search data
            
            cout << "Please enter a course as ABCD123:" << endl;
            cin >> name;
            course = courseTable->Search(name);
            if (!course.courseName.empty()) {
                printCourseInfo(course);
            }
            break;
        case 9:
            cout << "Goodbye!" << endl;
            break;

        default:
            cout << choice << " is not a valid menu option." << endl;
            break;
        }
    }
    return 0;
}

