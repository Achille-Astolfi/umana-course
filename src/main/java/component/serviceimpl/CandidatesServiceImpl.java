package component.serviceimpl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import iface.repository.CandidatesRepository;
import iface.service.CandidatesService;
import model.entity.Candidate;

@Service
public class CandidatesServiceImpl implements CandidatesService {
	@Autowired
	private CandidatesRepository candidatesRepository;

	@PostConstruct
	private void initService() {
		createCandidate(testCandidate("Achille", "Astolfi"));
		createCandidate(testCandidate("Rita", "Rossi"));
	}

	private Candidate testCandidate(String first, String last) {
		var candidate = new Candidate();
		candidate.setFirstName(first);
		candidate.setLastName(last);
		candidate.setEmailAddress(first.toLowerCase() + '.' + last.toLowerCase() + "@example.com");
		return candidate;
	}

	@Override
	@Transactional
	public Candidate createCandidate(Candidate candidate) {
		Long id = candidate.getId();
		if (id != null && candidatesRepository.existsById(id)
				|| candidatesRepository.findByEmailAddress(candidate.getEmailAddress()).isPresent()) {
			throw new EntityExistsException();
		}
		return candidatesRepository.save(candidate);
	}

	@Override
	public List<Candidate> readCandidatesAll() {
		return candidatesRepository.findAll();
	}

	@Override
	public Optional<Candidate> readCandidateById(Long id) {
		return candidatesRepository.findById(id);
	}

}
