package mobile_app.trainingpal.me.interfaces.callbacks;

import mobile_app.trainingpal.me.interfaces.models.ITrainingLog;

public interface IAPITrainingLogsCallback {

    public void OnSuccess(ITrainingLog[] trainingLogs);

    public void OnError(String errorMessage);
}
