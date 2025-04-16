package lk.ijse.heladivaproject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ArticleDTO {
    @NotNull(message = "Article ID cannot be null.")
    private String articleId;

    @NotBlank(message = "Title cannot be empty.")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters.")
    private String title;

    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Scientific name must contain only letters and spaces.")
    private String scientificName;

    @NotBlank(message = "Seasonality cannot be empty.")
    private String seasonality;

    @Pattern(regexp = "^[A-Za-z\\s,]+$", message = "Location must contain only letters, spaces, and commas.")
    private String location;

    @NotBlank(message = "Uses cannot be empty.")
    private String uses;

    @NotBlank(message = "Description cannot be empty.")
    private String description;

    private String healthBenefits;
    private String springCoordinates;
    private String summerCoordinates;
    private String autumnCoordinates;
    private String winterCoordinates;

    @NotBlank(message = "Author ID cannot be empty.")
    private String authorId;
    private Date publishedDate;


    private String tags;
    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid image URL format.")
    private String ImageUrl;
}
