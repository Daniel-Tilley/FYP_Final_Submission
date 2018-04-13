class TokenConstants:

    def __init__(self):
        pass

    PAYLOAD_USER_KEY = "user"
    PAYLOAD_ISSUER_KEY = "iss"
    PAYLOAD_ISSUER_VALUE = "trainingpal.me"
    PAYLOAD_ISSUE_TIME_KEY = "iat"
    PAYLOAD_EXP_KEY = "exp"
    PAYLOAD_EXP_TIME = 24

    RESPONSE_TOKEN_STRING_KEY = "TokenString"
    RESPONSE_CREATED_DATE_KEY = "CreatedDate"
    RESPONSE_EXPIRY_DATE_KEY = "ExpiryDate"
    RESPONSE_USER_ID_KEY = "UserId"
    RESPONSE_USER_TYPE_KEY = "UserType"

    DECODED_TEXT_FORMAT = "UTF-8"
