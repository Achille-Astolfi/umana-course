package model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.springframework.hateoas.Resource;

import model.entity.Candidate;

@JsonPropertyOrder({ "id", "firstName", "lastName", "emailAddress" })
@JsonIgnoreProperties("_links")
public class CandidateResource extends Resource<Candidate> {
	public CandidateResource(Candidate entity) {
		super(entity);
	}
}
