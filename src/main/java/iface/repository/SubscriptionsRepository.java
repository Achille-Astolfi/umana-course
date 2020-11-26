package iface.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.entity.Candidate;
import model.entity.Course;
import model.entity.Subscription;

public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {
	Optional<Subscription> findByCandidateAndCourse(Candidate candidate, Course course);

	List<Subscription> findByCourse(Course course);
}
