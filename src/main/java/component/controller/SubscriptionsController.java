package component.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import component.assembler.SubscriptionResourceAssembler;
import iface.service.SubscriptionsService;
import model.resource.SubscriptionResource;
import model.view.SubscriptionRequest;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionsController {
	@Autowired
	private SubscriptionsService subscriptionsService;
	@Autowired
	private SubscriptionResourceAssembler subscriptionResourceAssembler;

	@PostMapping
	public ResponseEntity<?> postResourceRequest(@RequestBody SubscriptionRequest subscriptionRequest) {
		try {
			var subscription = subscriptionsService.createSubscription(subscriptionRequest.getCandidate(),
					subscriptionRequest.getCourse());
			URI uri = linkTo(SubscriptionsController.class).slash(subscription.getId()).toUri();
			return ResponseEntity.created(uri).build();
		} catch (EntityNotFoundException enfe) {
			return ResponseEntity.notFound().build();
		} catch (EntityExistsException eee) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubscriptionResource> getSubscription(@PathVariable("id") long id) {
		return subscriptionsService.readSubscriptionById(id)//
				.map(subscriptionResourceAssembler::toResource)//
				.map(ResponseEntity::ok)//
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
