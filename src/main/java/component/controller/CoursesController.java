package component.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import component.assembler.CourseResourceAssembler;
import component.assembler.SubscriptionResourceAssembler;
import component.configuration.ForbiddenException;
import iface.service.CoursesService;
import iface.service.SubscriptionsService;
import model.entity.Course;
import model.entity.MvcUser;
import model.resource.CourseResource;

@RestController
@RequestMapping("/courses")
public class CoursesController {
	@Autowired
	private CoursesService coursesService;
	@Autowired
	private SubscriptionsService subscriptionsService;

	@Autowired
	private CourseResourceAssembler courseResourceAssembler;
	@Autowired
	private SubscriptionResourceAssembler subscriptionResourceAssembler;

	@GetMapping
	public ResponseEntity<Resources<CourseResource>> getCourses() {
		return ResponseEntity.ok(courseResourceAssembler.wrap(coursesService.readCoursesAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CourseResource> getCourse(@PathVariable("id") long id,
			@RequestAttribute("com.example.user") MvcUser user) {
		var maybeCourse = coursesService.readCourseById(id);
		if (maybeCourse.isPresent() && user.getUsername().equals("admin")) {
			var course = maybeCourse.get();
			var subscriptions = subscriptionsService.readSubscriptionsByCourse(course);
			subscriptions.stream()//
					.forEach((s) -> s.setCourse(null));
			var resources = subscriptionResourceAssembler.toResources(subscriptions);
			var courseResource = courseResourceAssembler.toResource(course);
			courseResource.setSubscriptions(resources);
			return ResponseEntity.ok(courseResource);
		}
		return maybeCourse//
				.map(courseResourceAssembler::toResource)//
				.map(ResponseEntity::ok)//
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> postCourses(@RequestBody Course course,
			@RequestAttribute("com.example.user") MvcUser user) {
		if (!user.getUsername().equals("admin")) {
			throw new ForbiddenException();
		}
		try {
			course.setDescription(StringUtils.trimWhitespace(course.getDescription()));
			course = coursesService.createCourse(course);
		} catch (EntityExistsException eee) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		URI uri = linkTo(CoursesController.class).slash(course.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
