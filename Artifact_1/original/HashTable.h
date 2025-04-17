#ifndef HASHTABLE_H
#define HASHTABLE_H

#include <string>
#include <vector>
#include "Course.h"
using namespace std;
class Node {
public:
    Course course;
    unsigned int key;
    Node() {
        key = UINT_MAX;
    }
    Node(Course course) {
        this->key = UINT_MAX;
        this->course = course;
    }
    Node(Course course, unsigned int key) {
        this->course = course;
        this->key = key;
    }
};
class HashTable {
public:
    HashTable();
    HashTable(unsigned int size);
    virtual ~HashTable();
    void Insert(Course course);
    Course Search(std::string courseId);
    unsigned int Hash(int key);
    int tableSize = 100;
    vector<vector<Node>> nodes;
};

#endif // !HASHTABLE_H
