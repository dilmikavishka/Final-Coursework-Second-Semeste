package lk.ijse.heladivaproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "articles")
    public class Article {
        @Id
        private String articleId;

        private String title;
        private String scientificName;
        private String seasonality;
        private String location;
        @Column(columnDefinition = "TEXT")
        private String uses;

        @Column(columnDefinition = "TEXT")
        private String description;
        private String healthBenefits;
        private String springCoordinates;
        private String summerCoordinates;
        private String autumnCoordinates;
        private String winterCoordinates;

        @ManyToOne
        @JoinColumn(name = "author_id", nullable = false)
        private User author;

        private Date publishedDate;
        private String tags;
    @Column(columnDefinition = "TEXT")
        private String ImageUrl;
    }
