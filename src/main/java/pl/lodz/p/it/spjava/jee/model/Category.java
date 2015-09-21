package pl.lodz.p.it.spjava.jee.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Krzysiek
 */
@Entity
@Table(name = "CATEGORY")
@NamedQueries({
    @NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
    @NamedQuery(name = "Category.findByIdCategory", query = "SELECT c FROM Category c WHERE c.idCategory = :idCategory"),
    @NamedQuery(name = "Category.findByCategory", query = "SELECT c FROM Category c WHERE c.category = :category")})
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CATEGORY", nullable = false)
    private Long idCategory;

    @Size(min = 2, max = 50)
    @Column(name = "CATEGORY", length = 50, nullable = false)
    @NotNull
    private String category;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Collection<Advert> advertCollection = new ArrayList<>();

    public Category() {
    }

    public Category(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<Advert> getAdvertCollection() {
        return advertCollection;
    }

    public void setAdvertCollection(Collection<Advert> advertCollection) {
        this.advertCollection = advertCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategory != null ? idCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.idCategory == null && other.idCategory != null) || (this.idCategory != null && !this.idCategory.equals(other.idCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.spjava.jee.model.Category[ idCategory=" + idCategory + " version= " + " ]";
    }

}
