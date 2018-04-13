package mobile_app.trainingpal.me.interfaces.callbacks;

import mobile_app.trainingpal.me.interfaces.models.IAuthObject;

public interface IAPIAuthCallback {

    public void OnSuccess(IAuthObject authObject);

    public void OnError(String errorMessage);
}
