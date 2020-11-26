package iface.service;

import java.util.List;
import java.util.Optional;

import model.entity.Course;

public interface CoursesService {

	List<Course> readCoursesAll();

	Optional<Course> readCourseById(Long id);

	Course createCourse(Course course);

}