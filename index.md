# CS-499 ePortfolio

## Introduction
My name is John Kirven, and this portfolio represents my final project prior to completing a Bachelor's degree Computer Science degree with a concentration in software engineering. This portfolio showcases my growth as a software developer across three core areas; software design and engineering, algorithms and data structures, and databases.


## Table of Contents
- [Self-Assessment](#self-assessment)
- [Enhancement One: Software Design and engineering](#enhancement-one-software-design-and-engineering)
	- [Github repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_1)
	- [Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_1/CS-499%20Milestone%20Two%20Narrative.docx)
- [Enhancement Two: Algorithms and Data Structures](#enhancement-two-algorithms-and-data-structures)
	- [Github repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_2)
	- [Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_2/CS-499%20Milestone%20Three%20Narrative.docx)
- [Enhancement Three: Databases](#enhancement-three-databases)
	- [Github repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_3)
	- [Narrative](https://github.com/johnathanki/johnathanki.github.io/blob/main/Artifact_3/CS-499%20Milestone%20Four%20Narrative.docx)


## Self-Assessment
I entered the Computer Science program with a fair bit of programming knowledge as it was a hobby of mine for many years, and this program expanded my knowledge far beyond what I ever could have achieved as a hobbyist. Some courses helped reinforce things I was already quite confident in such as problem solving, secure coding, writing clean code, object-oriented programming and other classes threw me entirely out of my element and introduced me to new and exciting things such as UI/UX design, full stack development, AI models, 3D graphics, and mobile application development. And in the interest of an honest self-assessment, there were classes that were the bane of my existence such as Physics and Software Reverse Engineering.

One thing I particularly enjoyed about my courses were the projects. Many of the projects throughout the program involved fictional companies or clients with either technical problems or requirements that I had to provide solutions to. This allowed me to use my skills to solve real-world problems, interpret requirements from a variety of sources, practice communication at various technical levels, and work within given constraints. 

While my coursework was done as a solo developer, with the only 'collaboration' being feedback from my instructors, making use of Github for every major project along with including detailed documentation laid the groundwork for collaborating in a team environment. Github has become a part of my day-to-day programming, weather solo or with a team it is very useful to have version control.

My communication skills have grown quite a bit throughout the CS program as well. I've written highly technical documentation, created presentations for non-technical clients/users, created detailed setup/installation instructions for both technical and non-technical users, as well as prototyping potential solutions for fictional clients. Learning how to explain development decisions clearly to both technical peers and non-technical stakeholders has been one of the most valuable and transferable skills I’ve developed.

Software engineering is something I deeply enjoy, and my previous experience compounded with the knowledge I've gained throughout this program has made me highly confident in my abilities to design and develop maintainable and scalable solutions. I'm incredibly proficient with Java, Python, C++, and C#. There are also languages I am less familiar with like Node.js and JavaScript in general, as well as languages I've not used in quite a while like PHP, Kotlin, and COBOL. Having said that, my knowledge programming principles, such as object-oriented design, control structures, and general algorithmic/logical thinking, allows me to adapt to new languages quite quickly. I've also learned quite a bit about security and secure coding, and have made it a habit to apply security best practices to all of my projects through input validation, authentication/authorization, encryption/hashing, and proper error handling. All three enhancements showcased in this portfolio are a testament to my skills as a software engineer, but my enhancement of the C++ hash table goes the extra mile to show my fluency in multiple languages and design principles.

Data structures and algorithms is also enjoyable. Picking the correct data structure or building a custom data structure to be efficient and effective is an enjoyable part of the problem solving process. As my knowledge has grown, so has my ability to identify multiple potential solutions and weigh them effectively based on performance and complexity. Enhancement one arguably does a better job of demonstrating my skills in algorithms and data structures than the enhancement dedicated to algorithms and data structures, but both show my ability to implement both custom and built-in data structures and develop algorithms to solve specific logical problems.

This brings us to our final concept; databases. Throughout the CS program I've become familiar with relational databases like MySQL and SQLite as well as non-relational databases like MongoDB and both have their own merits and best use cases. Developing a well-structured relational database certainly requires more initial effort than going with a non-relational NoSQL solution, but the structured data requirements and integrity offered by relational databases make them favorable for more complex systems. I've also become quite familiar with various tools for managing these databases such as MySQL Workbench, phpMyAdmin, and MongoDB Compass as well as working directly within a command line.



## Enhancement One: Software Design and Engineering
**About the artifact:**
This artifact is a C++ implementation of a hash table, which is a data structure that acts as a map for storing key-value pairs. A hash table stores data into buckets based off a hash function, which is advantageous when searching as only the bucket that holds the desired data needs to be searched, rather than searching the entire table. I wrote this program around two years ago, and it was more or less my first interaction with C++. This hash table implementation is used to store university courses, with each course having a prefix such as "MATH" or "CSCI", a course level such as 100 or 201, the name of the course, and a list of any prerequisites the course has.

[Original C++ Hash Table repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_1/original)
[Original C++ Hash Table README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_1/original/README.md)


**About the enhancement**
While this program perfectly fits the data structures and algorithms requirements the functionality required little tuning, so I instead decided to utilize it for the software design requirements. Since it was written while my comfort level with C++ was still growing, I assumed there were some design improvements that could be made. The final plan I landed on was porting the existing hash table structure to Java, while also creating an additional hash table structure using Java’s own HashTable implementation. This allowed me to showcase a deep understanding of both the Java programming language and various object-oriented programming (OOP) principles.

[Java Hash Table repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_1/enhanced)
[Java Hash Table README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_1/enhanced/README.md)




## Enhancement Two: Algorithms and Data Structures
**About the artifact:**
This artifact is an Android application built with Java whose primary purpose is to manage the inventory/stock of a warehouse. It supports simple (local) account creation and uses a local database to hold item information and user accounts. The app uses a model-view-viewmodel (MVVM) architectural pattern to separate the UI logic from the database data. The database is handled via Room, a library for implementing SQLite in Android and currently houses two tables, Account and InventoryItem. The app itself consists of three Activities; login/account creation, adding new items, and browsing the list of all items.

[Original Inventory App repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_2/original)
[Original Inventory App README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_2/original/README.md)


**About the enhancement**
The original implementation of the InventoryItem data structure was functional, but simple and lacking fields that it probably should’ve had such as a price and SKU. Additionally, the List that the database table populated was in an unspecified order and had no methods to sort the data by any meaningful metric. My plan was to remedy both by enhancing the data structure and implementing sorting algorithms so the user can choose a metric and direction to sort the List. Additionally, I wanted to clean up the existing code base and properly comment the code, two things I failed to do in the original due to time constraints.

[Enhanced Inventory App repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_2/enhanced)
[Enhanced Inventory App README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_2/enhanced/README.md)



## Enhancement Three: Databases
**About the artifact:**
This artifact is an Android application built with Java whose primary purpose is to manage the inventory/stock of a warehouse. It supports simple (local) account creation and uses a local database to hold item information and user accounts. The app uses a model-view-viewmodel (MVVM) architectural pattern to separate the UI logic from the database data. The database is handled via Room, a library for implementing SQLite in Android and currently houses two tables, Account and InventoryItem. The app itself consists of three Activities; login/account creation, adding new items, and browsing the list of all items.

[Original Inventory App repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_3/original)
[Original Inventory App README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_3/original/README.md)


**About the enhancement**
In the previous enhancement, the data structure of InventoryItem was altered to include more fields and sorting by those new fields was implemented. In this enhancement, the locally stored Room based SQLite database was replaced by a MySQL database controlled by a RESTful API constructed in Node.js/Express. To communicate with this new API, Entity classes (Account & InventoryItem) had their original annotations removed, the InventoryDatabase and DAO classes were removed entirely, and Retrofit was introduced to communicate with the API.

[Enhanced Inventory App + API repo](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_3/enhanced)
[Enhanced Inventory App + API README.md](https://github.com/johnathanki/johnathanki.github.io/tree/main/Artifact_3/enhanced/README.md)
