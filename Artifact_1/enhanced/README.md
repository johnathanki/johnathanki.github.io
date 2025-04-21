# Java Hash Table
This project consists of two in two phase:
1. A Java implementation of the [original C++ hash table](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_1/original/).
2. A performance benchmark comparing the custom implementation to Java’s built-in `Hashtable` class.


### Features
- A `CourseTable` that can be implemented into any table structure via polymorphism
- A custom hash table implementation
- A Java `HashTable` implementation
- A program to benchmark both hash table implementations
- `CourseGenerator` to generate a large number of unique `Course` objects for benchmarking


### Project structure
- `Hash Table/data` - Holds the `CourseList.csv` from the original project as well as a `Generated_Courses.csv` file containing thousands of generated courses for benchmarking
- `Hash Table/src/main/Main.java` - Entry point of the hash table implementation program
- `Hash Table/src/main/util/CourseGenerator.java` - Entry point to generate a new `Generated_Courses.csv` file
- `Hash Table/src/main/util/TableBenchmark.java` - Entry point of the benchmark program


### Benchmark Results
| Custom Hash Table Insertion (μs) | Custom Hash Table Search (ns) | Java's HashTable Insertion (μs) | Java's HashTable Search (ns) |
| --- | --- | --- | --- |
| 366 | 89 | 421 | 99 |
| 339 | 91 | 393 | 101 |
| 339 | 95 | 404 | 112 |
| 348 | 87 | 401 | 98 |
| 350 | 146 | 410 | 172 |
| 351 | 87 | 412 | 98 |
| 366 | 92 | 403 | 101 |
| 354 | 94 | 409 | 100 |
| 362 | 93 | 427 | 104 |
| 370 | 93 | 426 | 105 |
| Averages |   |   |   |			
| 354.5μs | 96.7ns | 410.6μs | 109ns |
\* Each insertion result represents the average time it takes for initialization and insertion of 10,374 courses 10,000 times
\* Each search result represents the average time it takes for a single search to execute, iterated 100,000 times.

With this specific use-case, and this specific benchmark, the custom hash table has measurably better performance than the Java `HashTable`


A narrative for this enhancement can be found here: [CS-499 Milestone Two Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_1/CS-499%20Milestone%20Two%20Narrative.docx)