package component.assembler;

import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import component.controller.CandidatesController;
import model.entity.Candidate;
import model.resource.CandidateResource;

@Component
public class CandidateResourceAssembler extends ResourceAssemblerSupport<Candidate, CandidateResource> {
	public CandidateResourceAssembler() {
		super(CandidatesController.class, CandidateResource.class);
	}

	@Override
	public CandidateResource toResource(Candidate entity) {
		return createResourceWithId(entity.getId(), entity);
	}

	@Override
	protected CandidateResource instantiateResource(Candidate entity) {
		return new CandidateResource(entity);
	}

	public Resources<CandidateResource> wrap(Iterable<Candidate> entities) {
		return new Resources<>(this.toResources(entities));
	}
}
