package com.gcv.labeldetection.entity;

public class Label {
	
	private String description;
	private float score;
	private float topicality;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public float getTopicality() {
		return topicality;
	}
	public void setTopicality(float topicality) {
		this.topicality = topicality;
	}
	
}
