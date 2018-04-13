from flask import Blueprint, request
from flask import json

from api_app.constants.request import RequestConstants
from api_app.dao.access_dao import AccessDao
from api_app.decorators.decorators import Decorators
from api_app.helpers.response_helper import ResponseHelper
from api_app.constants.database.coach_athlete_table import CoachAthleteTable
from api_app.models.coach_athlete import CoachAthlete
from api_app.models.update_data import UpdateData

mod_access = Blueprint('access', __name__, url_prefix='/access')


@mod_access.route("/athlete/revoke/<coach_id>", methods=['DELETE'])
@Decorators.TokenRequired
def RevokeCoachPrivileges(current_user, user_type, coach_id):

    if not coach_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()

    if access_dao.RevokeCoachAccess(current_user, coach_id):
        return ResponseHelper.ReturnOkResponse("Access Deleted!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Delete Access")


@mod_access.route("/athlete/grant/<coach_id>", methods=['POST'])
@Decorators.TokenRequired
def GrantCoachPrivileges(current_user, user_type, coach_id):

    if not coach_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)
    access = CoachAthlete(
        current_user,
        coach_id,
        request_data[CoachAthleteTable.CAN_ACCESS_TRAINING_LOG],
        request_data[CoachAthleteTable.CAN_ACCESS_TARGETS],
        request_data[CoachAthleteTable.IS_ACTIVE],
        request_data[CoachAthleteTable.START_DATE],
        request_data[CoachAthleteTable.INVITE_ID]
    )

    access_dao = AccessDao()

    if access_dao.CreateAccess(access):
        return ResponseHelper.ReturnOkResponse("Access Granted!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Grant Access")


@mod_access.route("/athlete/privileges/<coach_id>", methods=['PUT'])
@Decorators.TokenRequired
def UpdateCoachAccess(current_user, user_type, coach_id):

    if not coach_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)
    access = CoachAthlete(
        None,
        None,
        request_data[CoachAthleteTable.CAN_ACCESS_TRAINING_LOG],
        request_data[CoachAthleteTable.CAN_ACCESS_TARGETS],
        None,
        None,
        None,
    )

    access_dao = AccessDao()

    if access_dao.UpdateCoachAccess(current_user, coach_id, access):
        return ResponseHelper.ReturnOkResponse("Access Updated!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Update Access")


@mod_access.route("/athlete/invite/<invite_id>", methods=['PUT'])
@Decorators.TokenRequired
def UpdateCoachAccessFromInvite(current_user, user_type, invite_id):

    if not invite_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)

    update_data = UpdateData(
        request_data[RequestConstants.UPDATE_REQUEST_MEMBER_KEYS],
        request_data[RequestConstants.UPDATE_REQUEST_MEMBER_VALUES],
        [CoachAthleteTable.ATHLETE_ID, CoachAthleteTable.INVITE_ID],
        [current_user, invite_id],
    )

    if CoachAthleteTable.ATHLETE_ID in update_data.memberKeys or CoachAthleteTable.COACH_ID in update_data.memberKeys or CoachAthleteTable.INVITE_ID in update_data.memberKeys:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()

    if access_dao.UpdateFromInvite(update_data):
        return ResponseHelper.ReturnOkResponse("Access Updated!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Update Access")


@mod_access.route("/athlete/all/<coach_id>", methods=['GET'])
@Decorators.TokenRequired
def GetAthleteAccess(current_user, user_type, coach_id):

    if not coach_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_ATH:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()
    access = access_dao.CheckAccess(coach_id, current_user)

    if access:
        return ResponseHelper.ReturnOkDataResponse("Ok",  {"Access": access})

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Needs to request access!")


@mod_access.route("/coach/all/<athlete_id>", methods=['GET'])
@Decorators.TokenRequired
def GetCoachAccess(current_user, user_type, athlete_id):

    if not athlete_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_COA:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()
    access = access_dao.CheckAccess(current_user, athlete_id)
    if access:
        return ResponseHelper.ReturnOkDataResponse("Ok",  {"Access": access})

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Needs to request access!")


@mod_access.route("/coach/targets/<athlete_id>", methods=['GET'])
@Decorators.TokenRequired
def CheckCoachTargetAccess(current_user, user_type, athlete_id):

    if not athlete_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_COA:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()

    if access_dao.CheckCoachTargetsAccess(current_user, athlete_id):
        return ResponseHelper.ReturnOkResponse("Ok")

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Needs to request access!")


@mod_access.route("/coach/training-log/<athlete_id>", methods=['GET'])
@Decorators.TokenRequired
def CheckCoachTrainingLogAccess(current_user, user_type, athlete_id):
    if not athlete_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_COA:
        return ResponseHelper.ReturnBadRequestResponse()

    access_dao = AccessDao()

    if access_dao.CheckCoachTrainingLogAccess(current_user, athlete_id):
        return ResponseHelper.ReturnOkResponse("Ok")

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Needs to request access!")


@mod_access.route("/coach/request/<athlete_id>", methods=['POST'])
@Decorators.TokenRequired
def CoachRequestAccess(current_user, user_type, athlete_id):
    if not athlete_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if user_type != RequestConstants.USER_TYPE_COA:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)
    access = CoachAthlete(
        athlete_id,
        current_user,
        request_data[CoachAthleteTable.CAN_ACCESS_TRAINING_LOG],
        request_data[CoachAthleteTable.CAN_ACCESS_TARGETS],
        request_data[CoachAthleteTable.IS_ACTIVE],
        request_data[CoachAthleteTable.START_DATE],
        request_data[CoachAthleteTable.INVITE_ID]
    )

    access_dao = AccessDao()

    if access_dao.CreateCoachAccessWithInvite(access):
        return ResponseHelper.ReturnOkResponse("Ok")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to request access!")







