package component.assembler;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import component.controller.CoursesController;
import model.entity.Course;
import model.resource.CourseResource;

@Component
public class CourseResourceAssembler extends ResourceAssemblerSupport<Course, CourseResource> {
	public CourseResourceAssembler() {
		super(CoursesController.class, CourseResource.class);
	}

	@Override
	public CourseResource toResource(Course entity) {
		return createResourceWithId(entity.getId(), entity);
	}

	@Override
	protected CourseResource instantiateResource(Course entity) {
		return new CourseResource(entity);
	}

	public Resources<CourseResource> wrap(Iterable<Course> entities) {
		return new Resources<>(this.toResources(entities));
	}
}
