package mobile_app.trainingpal.me.interfaces.callbacks;

import mobile_app.trainingpal.me.interfaces.models.IUser;

public interface IAPIUserCallback {

    public void OnSuccess(IUser user);

    public void OnError(String errorMessage);
}
