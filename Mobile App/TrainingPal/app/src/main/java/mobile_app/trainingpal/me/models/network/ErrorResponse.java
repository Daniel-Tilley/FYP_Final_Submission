package mobile_app.trainingpal.me.models.network;

import mobile_app.trainingpal.me.interfaces.models.network.IErrorResponse;

public class ErrorResponse implements IErrorResponse {

    private String Message;
    private Object Data;

    public ErrorResponse(String Message, Object Data) {
        this.Message = Message;
        this.Data = Data;
    }

    public String getMessage() {
        return Message;
    }

    public Object getData() {
        return Data;
    }
}
