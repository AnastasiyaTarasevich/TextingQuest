package org.example.textingquest.mappers;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.entities.Review;
import org.example.textingquest.entities.Role;
import org.example.textingquest.entities.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDTOMapper implements Mapper<Review, ReviewDTO> {

    private static final ReviewDTOMapper INSTANCE=new ReviewDTOMapper();

    public static ReviewDTOMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public Review mapFrom(ReviewDTO reviewDTO) {
        return Review.builder()
                .name(reviewDTO.getName())
                .user_id(reviewDTO.getUser_id())
                .rate(reviewDTO.getRate())
                .description(reviewDTO.getDescription())
                .quest_id(reviewDTO.getQuest_id())
                .build();
    }
}
