package pl.lodz.p.it.spjava.jee.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Krzysiek
 */
@Entity
@Table(name = "STATUS")
@NamedQueries({
    @NamedQuery(name = "Status.findAll", query = "SELECT s FROM Status s"),
    @NamedQuery(name = "Status.findByIdStatus", query = "SELECT s FROM Status s WHERE s.idStatus = :idStatus"),
    @NamedQuery(name = "Status.findByStatus", query = "SELECT s FROM Status s WHERE s.status = :status")})
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_STATUS", nullable = false)
    private Long idStatus;

    @Size(min = 2, max = 50)
    @Column(name = "STATUS", length = 50, nullable = false)
    @NotNull
    private String status;

    public Status() {
    }

    public Status(Long idStatus) {
        this.idStatus = idStatus;
    }

    public Long getIdStatus() {
        return idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatus != null ? idStatus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        if ((this.idStatus == null && other.idStatus != null) || (this.idStatus != null && !this.idStatus.equals(other.idStatus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.jee.model.Status[ idStatus=" + idStatus +  " version= " + " ]";
    }

}
