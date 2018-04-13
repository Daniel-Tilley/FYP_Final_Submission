from flask import Blueprint

from api_app.helpers.response_helper import ResponseHelper

mod_home = Blueprint('home', __name__, url_prefix='/')


@mod_home.route("/")
def hello():
    return ResponseHelper.ReturnOkResponse("TrainingPal API V1.0")
