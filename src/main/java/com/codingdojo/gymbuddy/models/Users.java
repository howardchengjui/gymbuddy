package com.codingdojo.gymbuddy.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class Users {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Size(min = 1, message = "Name must be valid")
private String first_name;
@Size(min = 1, message = "Name must be valid")
private String last_name;
@Email(message = "Email must be valid")
private String email;
private String address;
@Size(min = 8, message = "Password must be at least 8 characters")
private String password;
private String age;
private String sex;
private String weight;
private String height;
private String benchpress;
private String squat;
private String goals;
private String deadlift;



@Transient
private String passwordConfirmation;
@Column(updatable=false)
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date createdAt;
@DateTimeFormat(pattern="yyyy-MM-dd")
private Date updatedAt;

@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
		name = "friends",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "friend_id")
		)
private List<Users>friends;

//messages Sent
@OneToMany(mappedBy="sender", fetch = FetchType.LAZY)
private List<Message> messagesSent;
//messages Sent
@OneToMany(mappedBy="receiver", fetch = FetchType.LAZY)
private List<Message> messagesRec;
///////////////// Getters & Setters ///////////////////////////////
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getFirst_name() {
	return first_name;
}
public void setFirst_name(String first_name) {
	this.first_name = first_name;
}
public String getLast_name() {
	return last_name;
}
public void setLast_name(String last_name) {
	this.last_name = last_name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPasswordConfirmation() {
	return passwordConfirmation;
}
public void setPasswordConfirmation(String passwordConfirmation) {
	this.passwordConfirmation = passwordConfirmation;
}
public Date getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
}
public Date getUpdatedAt() {
	return updatedAt;
}
public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getAge() {
	return age;
}
public void setAge(String age) {
	this.age = age;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getWeight() {
	return weight;
}
public void setWeight(String weight) {
	this.weight = weight;
}
public String getHeight() {
	return height;
}
public void setHeight(String height) {
	this.height = height;
}
public String getBenchpress() {
	return benchpress;
}
public void setBenchpress(String benchpress) {
	this.benchpress = benchpress;
}
public String getSquat() {
	return squat;
}
public void setSquat(String squat) {
	this.squat = squat;
}
public String getGoals() {
	return goals;
}
public void setGoals(String goals) {
	this.goals = goals;
}
public String getDeadlift() {
	return deadlift;
}
public void setDeadlift(String deadlift) {
	this.deadlift = deadlift;
}
public List<Users> getFriends() {
	return friends;
}
public void setFriends(List<Users> friends) {
	this.friends = friends;
}
public List<Message> getMessagesSent() {
	return messagesSent;
}
public void setMessagesSent(List<Message> messagesSent) {
	this.messagesSent = messagesSent;
}
public List<Message> getMessagesRec() {
	return messagesRec;
}
public void setMessagesRec(List<Message> messagesRec) {
	this.messagesRec = messagesRec;
}




}
