package com.onofftaxi.backend.model;

import com.vividsolutions.jts.geom.Point;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "geolocations")
public class Geolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position", nullable = false, columnDefinition = "POINT")
    private Point position;

    @OneToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "user_id")
    private Driver driver;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Geolocation)) {
            return false;
        }
        Geolocation other = (Geolocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Geolocation[ id=" + id + " ]";
    }
    
}
