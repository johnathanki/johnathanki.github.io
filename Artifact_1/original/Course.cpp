//============================================================================
// Name        : Course.cpp
// Author      : John Kirven
// Version     : 1.0
// Description : Course structure containing prefix, level, name, and prereqs
//============================================================================

#include "Course.h"

using namespace std;
Course::Course()
{
	courseLevel = -1;
	key = UINT_MAX;
}

Course::Course(std::string coursePrefix, int courseLevel, std::string courseName, int key, std::vector<std::string> prerequisites)
{
	this->coursePrefix = coursePrefix;
	this->courseLevel = courseLevel;
	this->courseName = courseName;
	this->key = key;
	this->prerequisites = prerequisites;
}

