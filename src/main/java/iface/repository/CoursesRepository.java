package iface.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.entity.Course;

public interface CoursesRepository extends JpaRepository<Course, Long> {

}
