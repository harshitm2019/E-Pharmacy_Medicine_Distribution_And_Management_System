package com.harshit.pharmacy.common.validator;

import com.harshit.pharmacy.common.constants.ErrorMessages;
import com.harshit.pharmacy.exception.DuplicateResourceException;
import org.springframework.stereotype.Component;

@Component
public class DuplicateValidator {

    public void validate(boolean exists, String fieldName) {

        if (exists)
            throw new DuplicateResourceException(String.format(

                    ErrorMessages.FIELD_ALREADY_EXISTS,
                    fieldName

            ));

    }

}
