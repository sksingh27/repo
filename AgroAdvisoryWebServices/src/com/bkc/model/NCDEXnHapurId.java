package com.bkc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "NCDEX")
@Embeddable
public class NCDEXnHapurId implements Serializable {
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	Date date;
	@Column(name = "source")
	String source;
	@Column(name = "month")
	String month;
    @Column(name="crop")
	String crop;
    @Column(name="product")
    String product;
    
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCrop() {
		return crop;
	}

	public void setCrop(String crop) {
		this.crop = crop;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
