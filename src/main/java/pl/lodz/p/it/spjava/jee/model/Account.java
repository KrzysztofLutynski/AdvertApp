package pl.lodz.p.it.spjava.jee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Krzysiek
 */
@Entity
@Table(name = "ACCOUNT")
@NamedQuery(name = "Account.findByVerification", query = "SELECT a FROM Account a WHERE a.verification = :x")//inUse
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_ACCOUNT", nullable = false)
    private Long idAccount;

    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "{constraint.email.wrongpattern}!")
    @Size(min = 6, max = 50, message = "{constraint.length.notinrange}")
    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    @NotNull(message = "{constraint.field.required}!")
    private String email;

    @Size(min = 2, max = 50, message = "{constraint.length.notinrange}")
    @Column(name = "FIRSTNAME", length = 50, nullable = false)
    @NotNull(message = "{constraint.field.required}!")
    private String firstName;

    @Size(min = 2, max = 50, message = "{constraint.length.notinrange}")
    @Column(name = "LASTNAME", length = 50, nullable = false)
    @NotNull(message = "{constraint.field.required}!")
    private String lastName;

    @Size(max = 255)
    @Column(name = "PASSWORD", length = 255, nullable = false)
    @NotNull(message = "{constraint.field.required}!")
    private String password;

    @Column(name = "ACTIVATIONDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activationDate;

    @Column(name = "LASTLOGINDATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;

    @Basic(optional = false)
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Size(max = 255)
    @Column(name = "VERIFICATION", length = 255)
    private String verification;

    @Version
    @Column(name = "VERSION", nullable = false)
    private long version;

    @JoinColumn(name = "ID_TYPE", referencedColumnName = "ID_TYPE", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Type type;

    @OneToMany(mappedBy = "buyerAccount", fetch = FetchType.LAZY)
    private Collection<Advert> advertCollection = new ArrayList<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Collection<Advert> advertCollection1 = new ArrayList<>();

    public Account() {
    }

    public Account(Long idAccount) {
        this.idAccount = idAccount;
    }

    public Account(Long idAccount, Boolean active) {
        this.idAccount = idAccount;
        this.active = active;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationdate) {
        this.activationDate = activationdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastlogindate) {
        this.lastLoginDate = lastlogindate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public Collection<Advert> getAdvertCollection() {
        return advertCollection;
    }

    public void setAdvertCollection(Collection<Advert> advertCollection) {
        this.advertCollection = advertCollection;
    }

    public Collection<Advert> getAdvertCollection1() {
        return advertCollection1;
    }

    public void setAdvertCollection1(Collection<Advert> advertCollection1) {
        this.advertCollection1 = advertCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccount != null ? idAccount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.idAccount == null && other.idAccount != null) || (this.idAccount != null && !this.idAccount.equals(other.idAccount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.jee.model.Account[ idAccount=" + idAccount + " version= " + version + " ]";
    }

}
