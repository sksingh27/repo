package com.bkc.model;
/**
 * @author Jitendra Kumar 
 */
import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@javax.persistence.Entity
@Table(name="nutrientDetails")
public class NutrientDetails  implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="nutrientId")
	private Integer NutrientId;
	
	@Column(name="cropId")
	private Integer CropId;	
	
	@Column(name="nutrientName")
	private String NutrientName;
	
	@Column(name="requireStage")
	private String RequireStage;
	
	@Column(name="requireSubStage")
	private String RequireSubStage;
	
	
	public String getRequireSubStage() {
		return RequireSubStage;
	}

	public void setRequireSubStage(String requireSubStage) {
		RequireSubStage = requireSubStage;
	}

	@Column(name="quantity")
	private Integer Quantity;
	
	@Column(name="noOfDays")
	private Integer NoOfDays;
	
	public Integer getNutrientId() {
		return NutrientId;
	}

	public void setNutrientId(Integer NutrientId) {
		this.NutrientId = NutrientId;
	}
	public Integer getCropId() {
		return CropId;
	}

	public void setCropId(Integer CropId) {
		this.CropId = CropId;
	}
	
	public String getNutrientName() {
		return NutrientName;
	}

	public void setNutrientName(String NutrientName) {
		this.NutrientName = NutrientName;
	}
	public String getRequireStage() {
		return RequireStage;
	}

	public void setRequireStage(String RequireStage) {
		this.RequireStage = RequireStage;
	}
	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer Quantity) {
		this.Quantity = Quantity;
	}
	public Integer getNoOfDays() {
		return NoOfDays;
	}

	public void setNoOfDays(Integer NoOfDays) {
		this.NoOfDays = NoOfDays;
	}
}
