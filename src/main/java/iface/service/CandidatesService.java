package iface.service;

import java.util.List;
import java.util.Optional;

import model.entity.Candidate;

public interface CandidatesService {

	Candidate createCandidate(Candidate candidate);

	Optional<Candidate> readCandidateById(Long id);

	List<Candidate> readCandidatesAll();

}