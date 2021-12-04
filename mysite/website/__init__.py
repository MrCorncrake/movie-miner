from flask import Flask

import os
STATIC_DIR = os.path.abspath('static')

app = Flask(__name__, static_folder=STATIC_DIR)
print(STATIC_DIR)
app.config['MAX_CONTENT_LENGTH'] = 30 * 1024 * 1024
app.config['UPLOAD_EXTENSIONS'] = ['.pdf', '.json']
app.config['UPLOAD_PATH'] = 'uploads'
app.config['DOWNLOAD_PATH'] = 'results'

def create_app():

    from .views import views
    from .process import process
    from .aboutus import aboutus

    app.register_blueprint(views, url_prefix='/')
    app.register_blueprint(process, url_prefix='/')
    app.register_blueprint(aboutus, url_prefix='/')

    return app