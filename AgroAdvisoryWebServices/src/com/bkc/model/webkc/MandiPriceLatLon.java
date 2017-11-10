package com.bkc.model.webkc;



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="mandiPriceLatLon"
    ,catalog="webkc"
)
public class MandiPriceLatLon  implements java.io.Serializable {


     private Integer id;
     private Date dateOfDownload;
     private String crop;
     private String mandi;
     private String state;
     private Float minPrice;
     private Float maxPrice;
     private Float modalPrice;
     private Float arrival;
     private String webSource;
     private String variety;
     private Float lat;
     private Float lon;

    public MandiPriceLatLon() {
    }

	
    public MandiPriceLatLon(Date dateOfDownload, String crop, String mandi, String state, String webSource) {
        this.dateOfDownload = dateOfDownload;
        this.crop = crop;
        this.mandi = mandi;
        this.state = state;
        this.webSource = webSource;
    }
    public MandiPriceLatLon(Date dateOfDownload, String crop, String mandi, String state, Float minPrice, Float maxPrice, Float modalPrice, Float arrival, String webSource, String variety, Float lat, Float lon) {
       this.dateOfDownload = dateOfDownload;
       this.crop = crop;
       this.mandi = mandi;
       this.state = state;
       this.minPrice = minPrice;
       this.maxPrice = maxPrice;
       this.modalPrice = modalPrice;
       this.arrival = arrival;
       this.webSource = webSource;
       this.variety = variety;
       this.lat = lat;
       this.lon = lon;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_of_download", nullable=false, length=10)
    public Date getDateOfDownload() {
        return this.dateOfDownload;
    }
    
    public void setDateOfDownload(Date dateOfDownload) {
        this.dateOfDownload = dateOfDownload;
    }

    
    @Column(name="crop", nullable=false, length=50)
    public String getCrop() {
        return this.crop;
    }
    
    public void setCrop(String crop) {
        this.crop = crop;
    }

    
    @Column(name="mandi", nullable=false, length=50)
    public String getMandi() {
        return this.mandi;
    }
    
    public void setMandi(String mandi) {
        this.mandi = mandi;
    }

    
    @Column(name="state", nullable=false, length=30)
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    
    @Column(name="min_price", precision=10)
    public Float getMinPrice() {
        return this.minPrice;
    }
    
    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    
    @Column(name="max_price", precision=10)
    public Float getMaxPrice() {
        return this.maxPrice;
    }
    
    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    
    @Column(name="modal_price", precision=10)
    public Float getModalPrice() {
        return this.modalPrice;
    }
    
    public void setModalPrice(Float modalPrice) {
        this.modalPrice = modalPrice;
    }

    
    @Column(name="arrival", precision=10)
    public Float getArrival() {
        return this.arrival;
    }
    
    public void setArrival(Float arrival) {
        this.arrival = arrival;
    }

    
    @Column(name="web_source", nullable=false, length=100)
    public String getWebSource() {
        return this.webSource;
    }
    
    public void setWebSource(String webSource) {
        this.webSource = webSource;
    }

    
    @Column(name="variety", length=30)
    public String getVariety() {
        return this.variety;
    }
    
    public void setVariety(String variety) {
        this.variety = variety;
    }

    
    @Column(name="lat", length=10)
    public Float getLat() {
        return this.lat;
    }
    
    public void setLat(Float lat) {
        this.lat = lat;
    }

    
    @Column(name="lon", length=10)
    public Float getLon() {
        return this.lon;
    }
    
    public void setLon(Float lon) {
        this.lon = lon;
    }




}


