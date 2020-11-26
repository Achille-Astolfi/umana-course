package component.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iface.repository.CoursesRepository;
import iface.service.CoursesService;
import model.entity.Course;

@Service
public class CoursesServiceImpl implements CoursesService {
	@Autowired
	private CoursesRepository coursesRepository;

	@PostConstruct
	private void init() {
		coursesRepository.save(testCourse("Digital Innovation Developer"));
		coursesRepository.save(testCourse("Corso Di Prova"));
	}

	public Course testCourse(String description) {
		var test = new Course();
		test.setDescription(description);
		return test;
	}

	@Override
	public List<Course> readCoursesAll() {
		return coursesRepository.findAll();
	}

	@Override
	public Optional<Course> readCourseById(Long id) {
		return coursesRepository.findById(id);
	}

	@Override
	@Transactional
	public Course createCourse(Course course) {
		Long id = course.getId();
		if (id != null && coursesRepository.existsById(id)) {
			throw new EntityExistsException();
		}
		return coursesRepository.save(course);
	}
}
