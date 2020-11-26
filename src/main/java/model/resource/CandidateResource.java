package model.resource;

import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import model.entity.Candidate;

@JsonPropertyOrder({ "id", "firstName", "lastName", "emailAddress" })
public class CandidateResource extends Resource<Candidate> {
	public CandidateResource(Candidate entity) {
		super(entity);
	}
}
