package mobile_app.trainingpal.me.interfaces.models;

public interface IAuthObject {

    public String getTokenString();

    public void setTokenString(String tokenString);

    public String getCreatedDate();

    public void setCreatedDate(String createdDate);

    public void setExpiryDate(String expiryDate);

    public String getExpiryDate();

    public String getUserId();

    public void setUserId(String userId);

    public String getUserType();

    public void setUserType(String userType);
}
