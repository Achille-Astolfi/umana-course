package component.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.resource.IndexResource;

@RestController
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public ResponseEntity<IndexResource> getResource() {
		return ResponseEntity.ok(new IndexResource());
	}
}
