package jp.gn.gonchan.util;


import jp.gn.gonchan.constant.DisplayMessageLevel;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessage {

    private List<String> infoMessages;
    private List<String> errorMessages;

    public DisplayMessage() {
        infoMessages = new ArrayList<>();
        errorMessages = new ArrayList<>();
    }

    /**
     * Set messages of the specified level.
     */
    public DisplayMessage(List<String> messages, DisplayMessageLevel level) {
        switch (level) {
            case INFO:
                infoMessages = messages;
                errorMessages = new ArrayList<>();
                break;
            case ERROR:
                infoMessages = new ArrayList<>();
                errorMessages = messages;
                break;
            default:
                infoMessages = new ArrayList<>();
                errorMessages = new ArrayList<>();
        }
    }

    public DisplayMessage(List<String> infoMessages, List<String> errorMessages) {
        this.infoMessages = infoMessages;
        this.errorMessages = errorMessages;
    }

    public List<String> getInfoMessages() {
        return infoMessages;
    }

    public void setInfoMessages(List<String> infoMessages) {
        this.infoMessages = infoMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DisplayMessage [infoMessages=");
        builder.append(infoMessages);
        builder.append(", errorMessages=");
        builder.append(errorMessages);
        builder.append("]");
        return builder.toString();
    }
}
