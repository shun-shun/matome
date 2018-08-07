package jp.gn.gonchan.validator;


import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BeanValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(ConstraintViolationException exception) {

        return null;
    }

    private String last(String str) {
        System.out.println(str);
        String[] arr = str.split("[.]");
        return arr[arr.length - 1];
    }

}
