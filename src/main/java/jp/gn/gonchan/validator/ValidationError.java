package jp.gn.gonchan.validator;

import jp.gn.gonchan.constant.AnnotationNameEnum;

/**
 * Holds information on the field that caused an error in the validation check.
 *
 * @author yu-oonishi
 */
public class ValidationError {
    private String fieldName;
    private AnnotationNameEnum annotation;
    private String message;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public AnnotationNameEnum getAnnotation() {
        return annotation;
    }

    public void setAnnotation(AnnotationNameEnum annotation) {
        this.annotation = annotation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ValidationError [fieldName=");
        builder.append(fieldName);
        builder.append(", annotation=");
        builder.append(annotation);
        builder.append(", message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }
}
