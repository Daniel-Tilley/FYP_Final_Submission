# Import flask and template operators
from flask import Flask
from flask_cors import CORS

# Define the WSGI application object
application = Flask(__name__)
CORS(application)

# Import a module / component using its blueprint handler variable (mod_auth)
from api_app.modules.auth.controllers import mod_auth as auth_module
from api_app.modules.home.controllers import mod_home as home_module
from api_app.modules.user.controllers import mod_user as user_module
from api_app.modules.training_log.controllers import mod_training_log as training_log_module
from api_app.modules.training_log_template.controllers import mod_training_log_template as training_log_template_module
from api_app.modules.target.controllers import mod_training_target as training_target_module
from api_app.modules.invite.controllers import mod_invite as invite_module
from api_app.modules.access.controllers import mod_access as access_module
from api_app.modules.event.controllers import mod_event as event_module

# Register blueprint(s)
application.register_blueprint(auth_module)
application.register_blueprint(home_module)
application.register_blueprint(user_module)
application.register_blueprint(training_log_module)
application.register_blueprint(training_log_template_module)
application.register_blueprint(training_target_module)
application.register_blueprint(invite_module)
application.register_blueprint(access_module)
application.register_blueprint(event_module)

# Configurations
application.config['SECRET_KEY'] = "xa5x04x88xb8xb8xc3xd5xedxb4xe6Fx92x9fxfdxbex8ax97xc9xffxa6"
application.config['PEPPER'] = "M8fk8EuDfsRgub5sFpj7"
