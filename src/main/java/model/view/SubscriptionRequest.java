package model.view;

public class SubscriptionRequest {
	private long course;
	private long candidate;

	public long getCourse() {
		return course;
	}

	public void setCourse(long course) {
		this.course = course;
	}

	public long getCandidate() {
		return candidate;
	}

	public void setCandidate(long candidate) {
		this.candidate = candidate;
	}
}
