package com.application.model;

public class EventModel {

	private String level;
	private String dateAndTime;
	private String source;
	private String eventId;
	private String taskCatagory;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getTaskCatagory() {
		return taskCatagory;
	}

	public void setTaskCatagory(String taskCatagory) {
		this.taskCatagory = taskCatagory;
	}

	@Override
	public String toString() {
		return "EventModel [level=" + level + ", dateAndTime=" + dateAndTime + ", source=" + source + ", eventId="
				+ eventId + ", taskCatagory=" + taskCatagory + "]";
	}

}
