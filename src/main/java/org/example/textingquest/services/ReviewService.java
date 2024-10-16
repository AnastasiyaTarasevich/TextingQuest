package org.example.textingquest.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.daos.ReviewDAO;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.dtos.UserDTO;
import org.example.textingquest.exceptions.ValidationException;
import org.example.textingquest.mappers.ReviewDTOMapper;
import org.example.textingquest.validators.CreateReviewValidator;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewService {
    private static final ReviewService INSTANCE = new ReviewService();

    private final ReviewDAO reviewDAO = ReviewDAO.getInstance();
    private final CreateReviewValidator createReviewValidator = CreateReviewValidator.getInstance();
    private final ReviewDTOMapper reviewDTOMapper = ReviewDTOMapper.getInstance();

    public static ReviewService getInstance() {
        return INSTANCE;
    }

    public void createReview(ReviewDTO reviewDTO) {
        var validationResult = createReviewValidator.isValid(reviewDTO);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var review = reviewDTOMapper.mapFrom(reviewDTO);
        reviewDAO.save(review);
    }
    public boolean existsReviewForQuest(int userId, int questId) {
        return reviewDAO.existsReviewForQuest(userId, questId);
    }
    public List<ReviewDTO> getAllReviews() {
        return reviewDAO.findAll();
    }

}
