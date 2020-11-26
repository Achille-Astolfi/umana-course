package model.domain;

import javax.validation.ConstraintViolation;

import org.springframework.hateoas.core.Relation;

@Relation(value = "constraintViolation", collectionRelation = "constraintViolations")
public class ConstraintViolationData {
	private String message;
	private String messageTemplate;
	private String propertyPath;

	public ConstraintViolationData() {

	}

	public ConstraintViolationData(ConstraintViolation<?> constraintViolation) {
		this.propertyPath = constraintViolation.getPropertyPath().toString();
		this.message = constraintViolation.getMessage();
		this.messageTemplate = constraintViolation.getMessageTemplate();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public String getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}
}
