package model.resource;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.hateoas.ResourceSupport;

import model.entity.Subscription;

@JsonPropertyOrder({ "id", "candidate", "course" })
@JsonIgnoreProperties("_links")
public class SubscriptionResource extends ResourceSupport {
	@JsonIgnore
	private Subscription entity;
	@JsonInclude(Include.NON_NULL)
	private CourseResource course;
	@JsonInclude(Include.NON_NULL)
	private CandidateResource candidate;

	public SubscriptionResource(Subscription entity) {
		super();
		this.entity = Objects.requireNonNull(entity);
	}

	@JsonProperty("id")
	public Long getEntityId() {
		return entity.getId();
	}

	public CourseResource getCourse() {
		return course;
	}

	public void setCourse(CourseResource course) {
		this.course = course;
	}

	public CandidateResource getCandidate() {
		return candidate;
	}

	public void setCandidate(CandidateResource candidate) {
		this.candidate = candidate;
	}
}
