package model.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;

import component.controller.CandidatesController;
import component.controller.CoursesController;
import component.controller.SubscriptionsController;

public class IndexResource extends ResourceSupport {
	public IndexResource() {
		super();
		add(linkTo(CoursesController.class).withRel("courses"));
		add(linkTo(CandidatesController.class).withRel("candidates"));
		add(linkTo(SubscriptionsController.class).withRel("subscriptions"));
	}
}
