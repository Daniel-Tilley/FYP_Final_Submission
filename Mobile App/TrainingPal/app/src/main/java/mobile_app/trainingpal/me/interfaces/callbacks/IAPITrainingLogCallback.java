package mobile_app.trainingpal.me.interfaces.callbacks;

import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;

public interface IAPITrainingLogCallback {

    public void OnSuccess(ITrainingLog trainingLog);

    public void OnError(String errorMessage);
}
