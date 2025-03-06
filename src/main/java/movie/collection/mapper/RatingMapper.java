package movie.collection.mapper;

import movie.collection.dto.RatingDto;
import movie.collection.dto.RatingSummary;
import movie.collection.model.Rating;
import movie.collection.model.User;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingDto entityToDto(Rating rating){
        return new RatingDto(rating.getId(),rating.getRating(),rating.getUser().getId(),rating.getMovie().getId());
    }
    public RatingSummary entityToSummary(Rating rating){
        int rate = rating.getRating();
        String title = rating.getMovie().getTitle();
        String username = rating.getUser().getUsername();
        Long id = rating.getId();
        return new RatingSummary(id,title,rate,username);
    }

}
