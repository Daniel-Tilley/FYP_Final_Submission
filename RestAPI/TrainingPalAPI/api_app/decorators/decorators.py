from functools import wraps

from flask import request
import jwt

from api_app.constants.response import ResponseConstants
from api_app.constants.request import RequestConstants
from api_app.constants.token import TokenConstants
from api_app.helpers.response_helper import ResponseHelper
from api_app.helpers.token_helper import TokenHelper


class Decorators:

    def __init__(self):
        pass

    @staticmethod
    def TokenRequired(f):
        @wraps(f)
        def decorated(*args, **kwargs):
            token = None

            if RequestConstants.REQUEST_TOKEN_IDENTIFIER in request.headers:
                split_string = request.headers[RequestConstants.REQUEST_TOKEN_IDENTIFIER].split(" ")

                auth_type = split_string[0]
                token = split_string[1]

                if auth_type != RequestConstants.REQUEST_TOKEN_AUTH_TYPE:
                    return ResponseHelper.ReturnBadRequestResponse()

            if not token:
                return ResponseHelper.ReturnBadRequestResponse()

            try:
                data = TokenHelper.DecodeTokenAndValidate(token)

                current_user = data[TokenConstants.PAYLOAD_USER_KEY]
                user_type = data[TokenConstants.RESPONSE_USER_TYPE_KEY]

            except jwt.ExpiredSignatureError:
                return ResponseHelper.ReturnUnauthorizedResponse("Token Has Expired!")

            except jwt.InvalidTokenError:
                return ResponseHelper.ReturnUnauthorizedResponse("Bad Token!")

            except Exception:
                return ResponseHelper.ReturnBadRequestResponse()

            return f(current_user, user_type, *args, **kwargs)

        return decorated
