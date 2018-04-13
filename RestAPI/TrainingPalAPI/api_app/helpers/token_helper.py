import datetime
import jwt

from api_app import application
from api_app.constants.token import TokenConstants


class TokenHelper:

    def __init__(self):
        pass

    @staticmethod
    def CreateToken(user_id, user_type):
        created_at_time = datetime.datetime.utcnow()
        expiry_time = created_at_time + datetime.timedelta(hours=TokenConstants.PAYLOAD_EXP_TIME)
        token = jwt.encode({
                TokenConstants.PAYLOAD_USER_KEY: user_id,
                TokenConstants.RESPONSE_USER_TYPE_KEY: user_type,
                TokenConstants.PAYLOAD_ISSUER_KEY: TokenConstants.PAYLOAD_ISSUER_VALUE,
                TokenConstants.PAYLOAD_ISSUE_TIME_KEY: created_at_time,
                TokenConstants.PAYLOAD_EXP_KEY: expiry_time
            }, application.config['SECRET_KEY'])

        return {
            TokenConstants.RESPONSE_TOKEN_STRING_KEY: TokenHelper.DecodeTokenToText(token),
            TokenConstants.RESPONSE_CREATED_DATE_KEY: created_at_time,
            TokenConstants.RESPONSE_EXPIRY_DATE_KEY: expiry_time,
            TokenConstants.RESPONSE_USER_ID_KEY: user_id,
            TokenConstants.RESPONSE_USER_TYPE_KEY: user_type
        }

    @staticmethod
    def DecodeTokenToText(token):
        return token.decode(TokenConstants.DECODED_TEXT_FORMAT)

    @staticmethod
    def DecodeTokenAndValidate(token):
        return jwt.decode(token, application.config['SECRET_KEY'])
