package pl.lodz.p.it.spjava.jee.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Krzysiek
 */
@Entity
@Table(name = "ADVERT")
@NamedQueries({
    @NamedQuery(name = "Advert.findByReserveDate", query = "SELECT a FROM Advert a WHERE a.advertReserveDate >= :x AND a.advertReserveDate <= :y AND a.status.idStatus=3"),//inUse
    @NamedQuery(name = "Advert.findByExpiryDate", query = "SELECT a FROM Advert a WHERE a.advertExpiryDate >= :x AND a.advertExpiryDate <= :y AND NOT a.status.idStatus=3"),//inUse
    @NamedQuery(name = "Advert.findByEditDate", query = "SELECT a FROM Advert a WHERE a.advertEditDate >= :x"),//inUse
    @NamedQuery(name = "Advert.findByCategory", query = "SELECT a FROM Advert a WHERE a.category.idCategory = :x"),//inUse
    @NamedQuery(name = "Advert.findByLastName", query = "SELECT a FROM Advert a WHERE a.account.lastName = :x"),//inUse
    @NamedQuery(name = "Advert.findByUserReserv", query = "SELECT a FROM Advert a WHERE a.buyerAccount.idAccount = :x" ), //inUse
    @NamedQuery(name = "Advert.findByIdAccount", query = "SELECT a FROM Advert a WHERE a.account.idAccount = :x")})//inUse
public class Advert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ADVERT", nullable = false)
    private Long idAdvert;

    @Size(min = 4, max = 50, message = "{constraint.length.notinrange}")
    @Column(name = "TITLE", length = 50, nullable = false)
    @NotNull(message = "{constraint.field.required}!")
    private String title;

    @Column(name = "PRICE", nullable =false, precision = 15, scale = 2)
    @NotNull(message = "{constraint.field.required}!")
    @Min(value = 0,message = "{constraint.price.negative}")
    private BigDecimal price;

    @Size(max = 255, message = "{constraint.length.notinrange}")
    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @Lob
    @Column(name = "PICTURE")
//    @Size(max = 1048576)
    private Serializable picture;

    @Column(name = "ADVERTADDDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date advertAddDate;

    @Column(name = "ADVERTEDITDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date advertEditDate;

    @Column(name = "ADVERTEXPIRYDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull(message = "{constraint.field.required}!")
    private Date advertExpiryDate;

    @Column(name = "ADVERTRESERVEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date advertReserveDate;

    @Version
    @Column(name = "VERSION", nullable = false)
    @NotNull
    private long version;

    @JoinColumn(name = "ID_USERBUYER", referencedColumnName = "ID_ACCOUNT")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account buyerAccount;

    @JoinColumn(name = "ID_ACCOUNT", referencedColumnName = "ID_ACCOUNT", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{constraint.field.required}!")
    private Account account;

    @JoinColumn(name = "ID_CATEGORY", referencedColumnName = "ID_CATEGORY", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{constraint.field.required}!")
    private Category category;

    @JoinColumn(name = "ID_STATUS", referencedColumnName = "ID_STATUS", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "{constraint.field.required}!")
    private Status status;

    public Advert() {
    }

    public Advert(Long idAdvert) {
        this.idAdvert = idAdvert;
    }

    public Long getIdAdvert() {
        return idAdvert;
    }
    
    public void setIdAdvert(Long idAdvert){
        this.idAdvert=idAdvert;
    }

    public Date getAdvertAddDate() {
        return advertAddDate;
    }

    public void setAdvertAddDate(Date advertAddDate) {
        this.advertAddDate = advertAddDate;
    }

    public Date getAdvertEditDate() {
        return advertEditDate;
    }

    public void setAdvertEditDate(Date advertEditDate) {
        this.advertEditDate = advertEditDate;
    }

    public Date getAdvertExpiryDate() {
        return advertExpiryDate;
    }

    public void setAdvertExpiryDate(Date advertExpiryDate) {
        this.advertExpiryDate = advertExpiryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Serializable getPicture() {
        return picture;
    }

    public void setPicture(Serializable picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getAdvertReserveDate() {
        return advertReserveDate;
    }

    public void setAdvertReserveDate(Date advertReserveDate) {
        this.advertReserveDate = advertReserveDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Account getBuyerAccount() {
        return buyerAccount;
    }

    public void SetBuyerAccount(Account buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAdvert != null ? idAdvert.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Advert)) {
            return false;
        }
        Advert other = (Advert) object;
        if ((this.idAdvert == null && other.idAdvert != null) || (this.idAdvert != null && !this.idAdvert.equals(other.idAdvert))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.jee.model.Advert[ idAdvert=" + idAdvert + " version= " + version + " ]";
    }

}
