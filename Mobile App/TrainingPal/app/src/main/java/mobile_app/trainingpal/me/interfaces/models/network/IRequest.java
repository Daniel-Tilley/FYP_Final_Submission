package mobile_app.trainingpal.me.interfaces.models.network;

import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;
import mobile_app.trainingpal.me.interfaces.models.IUser;

public interface IRequest {

    public String getBaseUrl();

    public IHeader getHeader();

    public IUser getBodyUser();

    public ITrainingLog getBodyTrainingLog();

    public IUpdateObject getBodyUpdateObject() ;
}