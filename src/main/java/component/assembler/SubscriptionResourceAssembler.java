package component.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import component.controller.SubscriptionsController;
import model.entity.Subscription;
import model.resource.SubscriptionResource;

@Component
public class SubscriptionResourceAssembler extends ResourceAssemblerSupport<Subscription, SubscriptionResource> {
	@Autowired
	private CandidateResourceAssembler candidateResourceAssembler;
	@Autowired
	private CourseResourceAssembler courseResourceAssembler;

	public SubscriptionResourceAssembler() {
		super(SubscriptionsController.class, SubscriptionResource.class);
	}

	@Override
	public SubscriptionResource toResource(Subscription entity) {
		var resource = createResourceWithId(entity.getId(), entity);
		var candidate = entity.getCandidate();
		if (candidate != null) {
			resource.setCandidate(candidateResourceAssembler.toResource(candidate));
		}
		var course = entity.getCourse();
		if (course != null) {
			resource.setCourse(courseResourceAssembler.toResource(course));
		}
		return resource;
	}

	@Override
	protected SubscriptionResource instantiateResource(Subscription entity) {
		return new SubscriptionResource(entity);
	}

	public Resources<SubscriptionResource> wrap(Iterable<Subscription> entities) {
		return new Resources<>(this.toResources(entities));
	}
}
