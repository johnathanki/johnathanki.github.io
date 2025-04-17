#ifndef COURSE_H
#define COURSE_H

#include <vector>
#include <string>
class Course {
public:
	Course();
	Course(std::string coursePrefix, int courseLevel, std::string courseName, int key, std::vector<std::string> prerequisites);
	std::string coursePrefix;
	int courseLevel;
	std::string courseName;
	int key;
	std::vector<std::string> prerequisites;

};

#endif // !COURSE_H