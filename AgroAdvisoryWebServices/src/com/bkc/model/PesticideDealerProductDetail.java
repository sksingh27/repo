package com.bkc.model;
// Generated 24 Apr, 2017 12:05:35 PM by Hibernate Tools 5.2.1.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * PesticideDealerProductDetail generated by hbm2java
 */
@Entity
@Table(name = "Pesticide_Dealer_Product_Detail", catalog = "Farmersportal")
public class PesticideDealerProductDetail implements java.io.Serializable {

	private PesticideDealerProductDetailId id;
	private String productGroupName;
	private String dealersProductName;

	public PesticideDealerProductDetail() {
	}

	public PesticideDealerProductDetail(PesticideDealerProductDetailId id) {
		this.id = id;
	}

	public PesticideDealerProductDetail(PesticideDealerProductDetailId id, String productGroupName,
			String dealersProductName) {
		this.id = id;
		this.productGroupName = productGroupName;
		this.dealersProductName = dealersProductName;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "dealerId", column = @Column(name = "Dealer_id", nullable = false)),
			@AttributeOverride(name = "sno", column = @Column(name = "SNo", nullable = false)) })
	public PesticideDealerProductDetailId getId() {
		return this.id;
	}

	public void setId(PesticideDealerProductDetailId id) {
		this.id = id;
	}

	@Column(name = "Product_group_name", length = 100)
	public String getProductGroupName() {
		return this.productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	@Column(name = "Dealers_product_name", length = 500)
	public String getDealersProductName() {
		return this.dealersProductName;
	}

	public void setDealersProductName(String dealersProductName) {
		this.dealersProductName = dealersProductName;
	}

}
