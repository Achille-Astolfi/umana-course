package iface.service;

import java.util.List;
import java.util.Optional;

import model.entity.Candidate;
import model.entity.Course;
import model.entity.Subscription;

public interface SubscriptionsService {

	Subscription createSubscription(Candidate candidate, Course course);

	Subscription createSubscription(long candidateId, long courseId);

	Optional<Subscription> readSubscriptionById(Long id);

	List<Subscription> readSubscriptionsByCourse(Course course);

}