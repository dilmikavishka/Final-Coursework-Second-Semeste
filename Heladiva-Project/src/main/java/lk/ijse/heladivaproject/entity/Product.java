package lk.ijse.heladivaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    private String productId;

    private String name;
    private String scientificName;
    private String seasonality;
    private String location;
    @Column(columnDefinition = "TEXT")
    private String uses;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String type;
    @Column(columnDefinition = "TEXT")
    private String healthBenefits;
    private String mapCoordinates;
    private Double price;
    private Integer stockAvailability;
    private String ImageUrl;
}
