# C++ Hash Table
This project is a standalone custom C++ hash table implementation. It was designed to load, store, and retrieve `Course` objects using a key derived from the course level.


### Features
- Course loading through CSV file
- Custom hashing function
- Collision resolution via linear probing
- Efficient insert and search


### Project Structure
- `Course.h` / `Course.cpp` the implementation of Course objects
- `HashTable.h` / `HashTable.cpp` the implementation of the HashTable including insert and search methods
- `ProjectTwo.cpp` entry point of the program with various helper methods to load and validate `course` objects
- `CourseList.csv` comma delimited list of course information


To view the enhanced version with algorithmic sorting of items see [Java Hash Table](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_1/enhanced/README.md)