package component.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import component.assembler.CandidateResourceAssembler;
import component.configuration.ForbiddenException;
import iface.service.CandidatesService;
import model.entity.Candidate;
import model.entity.MvcUser;
import model.resource.CandidateResource;

@RestController
@RequestMapping("/candidates")
public class CandidatesController {
	@Autowired
	private CandidatesService candidatesService;
	@Autowired
	private CandidateResourceAssembler candidateResourceAssembler;

	@GetMapping
	public ResponseEntity<Resources<CandidateResource>> getResources(
			@RequestAttribute("com.example.user") MvcUser user) {
		if (!user.getUsername().equals("admin")) {
			throw new ForbiddenException();
		}
		return ResponseEntity.ok(candidateResourceAssembler.wrap(candidatesService.readCandidatesAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CandidateResource> getCandidate(@PathVariable("id") long id) {
		return candidatesService.readCandidateById(id)//
				.map(candidateResourceAssembler::toResource)//
				.map(ResponseEntity::ok)//
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<?> postCandidates(@RequestBody Candidate candidate) {
		try {
			candidate.setFirstName(StringUtils.trimWhitespace(candidate.getFirstName()));
			candidate.setLastName(StringUtils.trimWhitespace(candidate.getLastName()));
			candidate = candidatesService.createCandidate(candidate);
		} catch (EntityExistsException eee) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		URI uri = linkTo(CandidatesController.class).slash(candidate.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
