package mobile_app.trainingpal.me.models.network;

import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.interfaces.models.IUser;
import mobile_app.trainingpal.me.interfaces.models.network.IHeader;
import mobile_app.trainingpal.me.interfaces.models.network.IRequest;
import mobile_app.trainingpal.me.interfaces.models.network.IUpdateObject;

public class Request implements IRequest{

    private String baseUrl;
    private IHeader header;
    private IUser bodyUser;
    private ITrainingLog bodyTrainingLog;
    private IUpdateObject bodyUpdateObject;

    public Request(String baseUrl, IHeader header) {
        this.baseUrl = baseUrl;
        this.header = header;
    }

    public Request(String baseUrl, IHeader header, IUser body) {
        this.baseUrl = baseUrl;
        this.header = header;
        this.bodyUser = body;
    }

    public Request(String baseUrl, IHeader header, ITrainingLog body) {
        this.baseUrl = baseUrl;
        this.header = header;
        this.bodyTrainingLog = body;
    }

    public Request(String baseUrl, IHeader header, IUpdateObject body) {
        this.baseUrl = baseUrl;
        this.header = header;
        this.bodyUpdateObject = body;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public IHeader getHeader() {
        return header;
    }

    public IUser getBodyUser() {
        return bodyUser;
    }

    public ITrainingLog getBodyTrainingLog() {
        return bodyTrainingLog;
    }

    public  IUpdateObject getBodyUpdateObject() {
        return bodyUpdateObject;
    }
}
