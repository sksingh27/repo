package com.bkc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "addNewCrop")
public class AddNewCrop {

	@Column(name = "userId")
	int userId;

	@Column(name = "cropCreationTIme")
	Date cropCreationTIme;

	public Date getCropCreationTIme() {
		return cropCreationTIme;
	}

	public void setCropCreationTIme(Date cropCreationTIme) {
		this.cropCreationTIme = cropCreationTIme;
	}

	@EmbeddedId
	AddNewCropId id;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public AddNewCropId getId() {
		return id;
	}

	public void setId(AddNewCropId id) {
		this.id = id;
	}

}
