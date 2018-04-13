package mobile_app.trainingpal.me.models;

import mobile_app.trainingpal.me.interfaces.models.IAuthObject;

public class AuthObject implements IAuthObject{

    private String TokenString = null;
    private String CreatedDate = null;
    private String ExpiryDate = null;
    private String UserId = null;
    private String UserType = null;

    public AuthObject() { }

    public String getTokenString() {
        return TokenString;
    }

    public void setTokenString(String tokenString) {
        this.TokenString = tokenString;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        this.CreatedDate = createdDate;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.ExpiryDate = expiryDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) { this.UserType = userType; }
}
