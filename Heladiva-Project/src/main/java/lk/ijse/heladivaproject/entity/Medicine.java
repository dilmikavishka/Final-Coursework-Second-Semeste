package lk.ijse.heladivaproject.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "medicine")
public class Medicine {
    @Id
    private String medicineId;
    private String name;
    private String disease;
    private String description;
    private String allergies;
    @ElementCollection
    @CollectionTable(name = "medicine_ingredients", joinColumns = @JoinColumn(name = "medicine_id"))
    private List<String> ingredients;
    private String preparation;
    @Column(columnDefinition = "TEXT") // Renamed from "usage"
    private String usageInstructions;
    @ElementCollection
    @CollectionTable(name = "medicine_side_effects", joinColumns = @JoinColumn(name = "medicine_id"))
    private List<String> sideEffects;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;


}
