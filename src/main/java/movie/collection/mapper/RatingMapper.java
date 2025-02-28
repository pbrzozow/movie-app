package movie.collection.mapper;

import movie.collection.dto.RatingDto;
import movie.collection.model.Rating;
import org.springframework.stereotype.Component;

@Component
public class RatingMapper {

    public RatingDto entityToDto(Rating rating){
        return new RatingDto(rating.getId(),rating.getRating(),rating.getUser().getId(),rating.getMovie().getId());
    }

}
