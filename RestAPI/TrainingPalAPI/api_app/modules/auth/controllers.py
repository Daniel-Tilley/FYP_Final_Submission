from flask import Blueprint, request
from flask import json

from api_app.dao.user_dao import UserDao
from api_app.decorators.decorators import Decorators
from api_app.helpers.pasword_hashing_helper import PasswordHashingHelper
from api_app.helpers.response_helper import ResponseHelper
from api_app.helpers.token_helper import TokenHelper
from api_app.constants.database.users_table import UsersTable

mod_auth = Blueprint('auth', __name__, url_prefix='/auth')


@mod_auth.route("/check-token")
@Decorators.TokenRequired
def hello(current_user, user_type):
    return ResponseHelper.ReturnOkResponse("Hello " + current_user)


@mod_auth.route("/login", methods=['GET'])
def Login():
    auth = request.authorization

    if not auth or not auth.username or not auth.password:
        return ResponseHelper.ReturnBadRequestResponse()

    user_dao = UserDao()
    user = user_dao.GetUserById(auth.username)

    if not user:
        return ResponseHelper.ReturnUnauthorizedResponse("User Does Not Exist!")

    if CheckPassword(auth.username, auth.password):
        try:
            return ResponseHelper.ReturnOkDataResponse(
                "User Authenticated",
                {"Token": TokenHelper.CreateToken(user[UsersTable.ID], user[UsersTable.TYPE])}
            )
        except:
            return ResponseHelper.ReturnErrorResponse()

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Incorrect Password!")


@mod_auth.route("/check-password", methods=['POST'])
@Decorators.TokenRequired
def CheckPassword(current_user, user_type):

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    else:

        user_data = json.loads(request.data)
        un_encrypted_password = user_data[str(UsersTable.PASSWORD)]

        if CheckPassword(current_user, un_encrypted_password):
            return ResponseHelper.ReturnOkResponse("Ok")

        else:
            return ResponseHelper.ReturnUnauthorizedResponse("Current Password is Incorrect!")

def CheckPassword(username, password):
    password_check_dao = UserDao()
    password_hash_object = password_check_dao.GetUserPasswordHash(username)

    if password_hash_object:

        if PasswordHashingHelper.VerifyEncryptedPasswordHash(password_hash_object[UsersTable.PASSWORD],
                                                             password):
            return True

        else:
            return None

    else:
        return None
