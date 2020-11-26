package model.resource;

import java.util.List;

import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import model.entity.Course;

@JsonPropertyOrder({ "id", "descrizione" })
public class CourseResource extends Resource<Course> {
	@JsonInclude(Include.NON_NULL)
	List<SubscriptionResource> subscriptions;

	public CourseResource(Course content) {
		super(content);
	}

	public List<SubscriptionResource> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<SubscriptionResource> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
