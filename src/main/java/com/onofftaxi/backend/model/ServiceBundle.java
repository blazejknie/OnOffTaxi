package com.onofftaxi.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "services")
public class ServiceBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Driver driver;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private AvailableService enumeratedName;

    @Column(name = "price")
    private String price;

    public String getReadableName() {
        return enumeratedName.getReadableName();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ServiceBundle)) {
            return false;
        }
        ServiceBundle other = (ServiceBundle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Services[ id=" + id + " ]";
    }

}
