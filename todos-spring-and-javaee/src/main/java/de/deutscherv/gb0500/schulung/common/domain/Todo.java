package de.deutscherv.gb0500.schulung.common.domain;

import java.time.LocalDate;

public class Todo {

	private String title;
	private String description;
	private LocalDate dueDate;
	private boolean done; // enum?

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Todo [title=" + title + ", description=" + description + ", dueDate=" + dueDate + ", done=" + done
				+ "]";
	}

}
