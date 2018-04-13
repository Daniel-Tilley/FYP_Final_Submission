from flask import Blueprint, request
from flask import json

from api_app.constants.database.users_table import UsersTable
from api_app.dao.user_dao import UserDao
from api_app.decorators.decorators import Decorators
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.pasword_hashing_helper import PasswordHashingHelper
from api_app.helpers.response_helper import ResponseHelper
from api_app.models.update_data import UpdateData
from api_app.models.user import User
from api_app.constants.request import RequestConstants

mod_user = Blueprint('user', __name__, url_prefix='/users')


@mod_user.route("", methods=['POST'])
def CreateUser():

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    else:
        user_data = json.loads(request.data)

        password = PasswordHashingHelper.GetEncryptedHash(user_data[str(UsersTable.PASSWORD)])

        user = User(
            user_data[str(UsersTable.ID)],
            password,
            user_data[str(UsersTable.F_NAME)],
            user_data[str(UsersTable.L_NAME)],
            user_data[str(UsersTable.E_MAIL)],
            user_data[str(UsersTable.DOB)],
            user_data[str(UsersTable.TYPE)],
            user_data[str(UsersTable.LOCATION)],
            user_data[str(UsersTable.BIO)]
        )

        user_dao = UserDao()

        if user_dao.GetUserById(user.Id):

            return ResponseHelper.ReturnConflictResponse("User Already Exists!")

        else:
            user_dao = UserDao()

            if user_dao.CreateUser(user):

                return ResponseHelper.ReturnCreatedResponse("User Created")

            else:

                return ResponseHelper.ReturnCreatedResponse("Unable to Create User!")


@mod_user.route("/<user_id>", methods=['PUT'])
@Decorators.TokenRequired
def UpdateUser(current_user, user_type, user_id):

    if current_user != user_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    else:
        request_data = json.loads(request.data)

        update_data = UpdateData(
            request_data[RequestConstants.UPDATE_REQUEST_MEMBER_KEYS],
            request_data[RequestConstants.UPDATE_REQUEST_MEMBER_VALUES],
            [UsersTable.ID],
            [user_id],
        )

        if UsersTable.ID in update_data.memberKeys:

            return ResponseHelper.ReturnBadRequestResponse()

        if UsersTable.PASSWORD in update_data.memberKeys:

            update_data.memberValues[update_data.memberKeys.index(UsersTable.PASSWORD)] = \
                PasswordHashingHelper.GetEncryptedHash(update_data.memberValues[
                                                           update_data.memberKeys.index(UsersTable.PASSWORD)])

        user_dao = UserDao()

        if user_dao.UpdateUser(update_data):

            return ResponseHelper.ReturnOkResponse("User Updated")

        else:

            return ResponseHelper.ReturnConflictResponse("Unable to Update User!")


@mod_user.route("/<username>", methods=['GET'])
@Decorators.TokenRequired
def GetUser(current_user, user_type, username):
    if not username:
        return ResponseHelper.ReturnBadRequestResponse()

    user_dao = UserDao()

    if username == current_user:
        user = user_dao.GetUserById(current_user)

        if user:
            return ResponseHelper.ReturnOkDataResponse(
                "User Found",
                {"User": DAOHelper.RemoveValueFromObject(UsersTable.PASSWORD, user)}
            )

        else:
            return ResponseHelper.ReturnNotFoundResponse("User Not Found!")

    else:
        user = user_dao.GetUserById(username)

        if user:
            return ResponseHelper.ReturnOkDataResponse(
                "Request OK",
                {"User": DAOHelper.RemoveValueFromObject(UsersTable.PASSWORD, user)}
            )

        else:
            return ResponseHelper.ReturnNotFoundResponse("User Not Found!")


@mod_user.route("/search/<search_type>/<search_value>", methods=['GET'])
@Decorators.TokenRequired
def GetUsers(current_user, user_type, search_type, search_value):
    if not search_type and search_value:
        return ResponseHelper.ReturnBadRequestResponse()

    user_dao = UserDao()
    users = user_dao.GetUsers(search_type, search_value)

    if users:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Users": users}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("Users Not Found!")


@mod_user.route("/athlete/coaches", methods=['GET'])
@Decorators.TokenRequired
def GetCoaches(current_user, user_type):

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    user_dao = UserDao()
    users = user_dao.GetAthleteCoaches(current_user)

    if users:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Users": users}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("Users Not Found!")


@mod_user.route("/coach/athletes", methods=['GET'])
@Decorators.TokenRequired
def GetAthletes(current_user, user_type):

    if user_type != RequestConstants.USER_TYPE_COA:
        return ResponseHelper.ReturnBadRequestResponse()

    user_dao = UserDao()
    users = user_dao.GetCoachAthletes(current_user)

    if users:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Users": users}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("Users Not Found!")
