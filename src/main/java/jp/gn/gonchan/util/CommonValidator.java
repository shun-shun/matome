package jp.gn.gonchan.util;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author yu-oonishi
 */
public class CommonValidator<T> {

    /**
     * Execute validation.
     *
     * @param form Target form
     */
    public List<String> validate(T form) {
        ValidatorFactory vf = Validation.byDefaultProvider().configure()
                .messageInterpolator(new ResourceBundleMessageInterpolator(new ResourceBundleLocator() {

                    @Override
                    public ResourceBundle getResourceBundle(Locale arg0) {
                        return ResourceBundle.getBundle("ValidationMessages", new PropertyControler());
                    }
                })).buildValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<T>> cv = validator.validate(form);

        List<String> errorMessages = new ArrayList<>();
        cv.forEach(error -> errorMessages.add(error.getMessage()));
        return errorMessages;
    }
}
