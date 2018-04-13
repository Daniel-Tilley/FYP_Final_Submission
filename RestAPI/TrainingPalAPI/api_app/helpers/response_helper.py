from flask import jsonify

from api_app.constants.response import ResponseConstants


class ResponseHelper:

    def __init__(self):
        pass

    @staticmethod
    def CreateResponseObject(code, message, data):
        return jsonify({"Message": message, "Data": data}), code

    @staticmethod
    def CreateNoDataResponseObject(code, message):
        return jsonify({"Message": message, "Data": None}), code

    # 200
    @staticmethod
    def ReturnOkResponse(message):
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_OK,
            message
        )

    # 200
    @staticmethod
    def ReturnOkDataResponse(message, data):
        return ResponseHelper.CreateResponseObject(
            ResponseConstants.CODE_OK,
            message,
            data
        )

    # 201
    @staticmethod
    def ReturnCreatedResponse(message):
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_CREATED,
            message
        )

    # 204
    @staticmethod
    def ReturnNoContentResponse():
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_CONTENT,
            None,
        )

    # 400
    @staticmethod
    def ReturnBadRequestResponse():
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_BAD_REQUEST,
            "Bad Request"
        )

    # 401
    @staticmethod
    def ReturnUnauthorizedResponse(message):
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_UNAUTHORISED,
            message
        )

    # 404
    @staticmethod
    def ReturnNotFoundResponse(message):
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_NOT_FOUND,
            message
        )

    # 409
    @staticmethod
    def ReturnConflictResponse(message):
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_CONFLICT,
            message
        )

    # 500
    @staticmethod
    def ReturnErrorResponse():
        return ResponseHelper.CreateNoDataResponseObject(
            ResponseConstants.CODE_BAD_REQUEST,
            "An Error Occurred"
        )
