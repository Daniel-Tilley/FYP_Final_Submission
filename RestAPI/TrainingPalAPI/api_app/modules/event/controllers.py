from flask import Blueprint, request
from flask import json

from api_app.constants.database.events_table import EventsTable
from api_app.constants.request import RequestConstants
from api_app.dao.event_dao import EventDao
from api_app.dao.invite_dao import InviteDao
from api_app.decorators.decorators import Decorators
from api_app.helpers.response_helper import ResponseHelper
from api_app.models.event import Event
from api_app.models.update_data import UpdateData

mod_event = Blueprint('event', __name__, url_prefix='/events')


@mod_event.route("/event", methods=['POST'])
@Decorators.TokenRequired
def CreateEvent(current_user, user_type):

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    event_data = json.loads(request.data)

    event = Event(
        None,
        event_data[str(EventsTable.TYPE)],
        event_data[str(EventsTable.NAME)],
        event_data[str(EventsTable.HOST_USERNAME)],
        event_data[str(EventsTable.CREATED_DATE)],
        event_data[str(EventsTable.EVENT_DATE)],
        None
    )

    event_dao = EventDao()
    event_id = event_dao.CreateEvent(event)

    if event_id:
        return ResponseHelper.ReturnOkDataResponse("Event Created!", event_id)

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Create Event!")


@mod_event.route("/event/<event_id>", methods=['DELETE'])
@Decorators.TokenRequired
def DeleteEvent(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    host_check_dao = EventDao()

    if not host_check_dao.CheckHostAccess(current_user, event_id):
        return ResponseHelper.ReturnBadRequestResponse()

    invite_dao = InviteDao()
    invite_dao.DeleteInvitesFromEvent(event_id)

    event_dao = EventDao()

    if event_dao.DeleteEvent(event_id):
        return ResponseHelper.ReturnOkResponse("Event Deleted!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Delete Event!")


@mod_event.route("/event/<event_id>", methods=['PUT'])
@Decorators.TokenRequired
def UpdateEvent(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    host_check_dao = EventDao()

    if not host_check_dao.CheckHostAccess(current_user, event_id):
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)

    update_data = UpdateData(
        request_data[RequestConstants.UPDATE_REQUEST_MEMBER_KEYS],
        request_data[RequestConstants.UPDATE_REQUEST_MEMBER_VALUES],
        [EventsTable.ID],
        [event_id],
    )

    if EventsTable.ID in update_data.memberKeys:
        return ResponseHelper.ReturnBadRequestResponse()

    if EventsTable.HOST_USERNAME in update_data.memberKeys:
        return ResponseHelper.ReturnBadRequestResponse()

    if EventsTable.CREATED_DATE in update_data.memberKeys:
        return ResponseHelper.ReturnBadRequestResponse()

    event_dao = EventDao()

    if event_dao.UpdateEvent(update_data):
        return ResponseHelper.ReturnOkResponse("Event Updated!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Update Event!")


@mod_event.route("/hosted", methods=['GET'])
@Decorators.TokenRequired
def GetHostedEvents(current_user, user_type):

    event_dao = EventDao()
    events = event_dao.GetHostedEvents(current_user)

    if events:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Events": events}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("No Events Found!")


@mod_event.route("/attending", methods=['GET'])
@Decorators.TokenRequired
def GetAttendingEvents(current_user, user_type):

    event_dao = EventDao()
    events = event_dao.GetParticipatingEvents(current_user)

    if events:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Events": events}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("No Events Found!")


@mod_event.route("/event/<event_id>", methods=['GET'])
@Decorators.TokenRequired
def GetEvent(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    event_dao = EventDao()
    event = event_dao.GetEvent(event_id)

    if event:
        return ResponseHelper.ReturnOkDataResponse(
            "Request OK",
            {"Event": event}
        )

    else:
        return ResponseHelper.ReturnNotFoundResponse("No Event Found!")


@mod_event.route("/access/<event_id>", methods=['GET'])
@Decorators.TokenRequired
def CanAccess(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    event_dao = EventDao()
    can_access = event_dao.CanAccess(event_id, current_user)

    if can_access:
        return ResponseHelper.ReturnOkDataResponse("Ok", can_access)

    else:
        return ResponseHelper.ReturnUnauthorizedResponse("Needs to request access!")


@mod_event.route("/participants/<event_id>", methods=['POST'])
@Decorators.TokenRequired
def CreateParticipants(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    host_check_dao = EventDao()

    if not host_check_dao.CheckHostAccess(current_user, event_id):
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)

    event_dao = EventDao()

    if event_dao.CreateParticipants(event_id, request_data["Users"]):
        return ResponseHelper.ReturnOkResponse("Participants Added!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Add Participants!")


@mod_event.route("/participants/<event_id>", methods=['PUT'])
@Decorators.TokenRequired
def AcceptParticipants(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    event_access_dao = EventDao()

    if not event_access_dao.CanAccess(event_id, current_user):
        return ResponseHelper.ReturnBadRequestResponse()

    event_dao = EventDao()

    if event_dao.AcceptParticipant(event_id, current_user):
        return ResponseHelper.ReturnOkResponse("Participants Accepted!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Accept Participants!")


@mod_event.route("/par-delete/<event_id>", methods=['PUT'])
@Decorators.TokenRequired
def RemoveParticipants(current_user, user_type, event_id):

    if not event_id:
        return ResponseHelper.ReturnBadRequestResponse()

    if not request.data:
        return ResponseHelper.ReturnBadRequestResponse()

    event_access_dao = EventDao()

    if not event_access_dao.CanAccess(event_id, current_user):
        return ResponseHelper.ReturnBadRequestResponse()

    request_data = json.loads(request.data)
    event_dao = EventDao()

    if event_dao.RemoveParticipants(event_id, request_data["Users"]):
        return ResponseHelper.ReturnOkResponse("Participants Deleted!")

    else:
        return ResponseHelper.ReturnConflictResponse("Unable to Delete Participants!")

