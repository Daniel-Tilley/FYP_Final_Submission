from flask import Blueprint, request
from flask import json

from api_app.dao.access_dao import AccessDao
from api_app.dao.target_dao import TargetDao
from api_app.models.target import Target
from api_app.decorators.decorators import Decorators
from api_app.helpers.response_helper import ResponseHelper
from api_app.models.update_data import UpdateData
from api_app.constants.request import RequestConstants
from api_app.constants.database.targets_table import TargetsTable

mod_training_target = Blueprint('training_target', __name__, url_prefix='/targets')


@mod_training_target.route("/<user_id>", methods=['POST'])
@Decorators.TokenRequired
def CreateTarget(current_user, user_type, user_id):

    if current_user != user_id:

        access_dao = AccessDao()

        if not access_dao.CheckCoachTargetsAccess(current_user, user_id):
            return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    else:
        request_data = json.loads(request.data)

        target = Target(
            None,  # For Id that is auto generated by a trigger
            user_id,  # Ensure log is created for the current user,
            request_data[str(TargetsTable.CONTENT)],
            request_data[str(TargetsTable.STATUS)],
            request_data[str(TargetsTable.WEEK)],
            request_data[str(TargetsTable.YEAR)]
        )

        target_dao = TargetDao()

        if target_dao.CreateTarget(target):

            return ResponseHelper.ReturnCreatedResponse("Target Created")

        else:

            return ResponseHelper.ReturnConflictResponse("Unable to Create Target!")


@mod_training_target.route("/<user_id>/<target_id>", methods=['PUT'])
@Decorators.TokenRequired
def UpdateTarget(current_user, user_type, user_id, target_id):

    if current_user != user_id:

        access_dao = AccessDao()

        if not access_dao.CheckCoachTargetsAccess(current_user, user_id):
            return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    else:
        request_data = json.loads(request.data)

        update_data = UpdateData(
            request_data[RequestConstants.UPDATE_REQUEST_MEMBER_KEYS],
            request_data[RequestConstants.UPDATE_REQUEST_MEMBER_VALUES],
            [TargetsTable.ID, TargetsTable.ATHLETE_ID],
            [target_id, user_id]
        )

        if TargetsTable.ID in update_data.memberKeys:

            return ResponseHelper.ReturnBadRequestResponse()

        if TargetsTable.ATHLETE_ID in update_data.memberKeys:

            return ResponseHelper.ReturnBadRequestResponse()

        target_dao = TargetDao()

        if target_dao.UpdateTarget(update_data):
            return ResponseHelper.ReturnOkResponse("Target Updated")

        else:
            return ResponseHelper.ReturnConflictResponse("Unable to Update Target!")


@mod_training_target.route("/<user_id>/<target_id>", methods=['DELETE'])
@Decorators.TokenRequired
def DeleteTarget(current_user, user_type, user_id, target_id):

    if current_user != user_id:

        access_dao = AccessDao()

        if not access_dao.CheckCoachTargetsAccess(current_user, user_id):
            return ResponseHelper.ReturnBadRequestResponse()

    target_dao = TargetDao()

    if target_dao.DeleteTarget(user_id, target_id):
        return ResponseHelper.ReturnOkResponse("Target Deleted")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Delete Target!")


@mod_training_target.route("/<user_id>/<week_num>/<year_num>", methods=['GET'])
@Decorators.TokenRequired
def GetTargets(current_user, user_type, user_id, week_num, year_num):

    if current_user != user_id:

        access_dao = AccessDao()

        if not access_dao.CheckCoachTargetsAccess(current_user, user_id):
            return ResponseHelper.ReturnBadRequestResponse()
    
    if not week_num:
        return ResponseHelper.ReturnBadRequestResponse()
    
    target_dao = TargetDao()
    targets = target_dao.GetTargetsByWeek(user_id, week_num, year_num)

    if targets:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Targets": targets}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("No Targets Found!")


