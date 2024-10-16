package org.example.textingquest.validators;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.textingquest.dtos.ReviewDTO;
import org.example.textingquest.services.UserService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateReviewValidator implements Validator<ReviewDTO> {

    private static final CreateReviewValidator INSTANCE=new CreateReviewValidator();

    public static CreateReviewValidator getInstance() {
        return INSTANCE;
    }
    @Override
    public ValidationResult isValid(ReviewDTO object) {
        var validationResult=new ValidationResult();


        if(object.getRate() == null)
        {
            validationResult.add(Error.of("invalid.rate","Rate is invalid"));

        }

        return validationResult;
    }
}
