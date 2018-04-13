package mobile_app.trainingpal.me.interfaces.callbacks;

public interface IAPIResponseCallback {

    public void OnSuccess(String successMessage);

    public void OnError(String errorMessage);
}
