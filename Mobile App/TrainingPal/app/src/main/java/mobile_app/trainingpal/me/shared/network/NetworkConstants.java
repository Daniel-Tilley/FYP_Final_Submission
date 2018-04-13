package mobile_app.trainingpal.me.shared.network;

public final class NetworkConstants {

    private NetworkConstants() { }

    // Headers
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String BASIC_AUTH_TYPE = "Basic ";
    public static final String BEARER_AUTH_TYPE = "Bearer ";

    // Tags
    public static final String AUTH_TAG = "Auth_Tag";
    public static final String USER_TAG = "User_Tag";
    public static final String TRAINING_LOG_TAG = "Training_Log_Tag";

    // Base response keys
    public static final String DATA_KEY = "Data";
    public static final String MESSAGE_KEY = "Message";

    // Data Keys
    public static final String TOKEN_KEY = "Token";

    public static final String USER_KEY = "User";
    public static final String USERS_KEY = "Users";

    public static final String TRAINING_LOG_KEY = "TrainingLog";
    public static final String TRAINING_LOGS_KEY = "TrainingLogs";

    // Error
    public static final String DEFAULT_ERROR_MESSAGE = "An Error Has Occurred!";
}
