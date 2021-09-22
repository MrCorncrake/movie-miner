from flask import Flask

app = Flask(__name__)
app.config['MAX_CONTENT_LENGTH'] = 30 * 1024 * 1024
app.config['UPLOAD_EXTENSIONS'] = ['.pdf']
app.config['UPLOAD_PATH'] = 'uploads'
app.config['DOWNLOAD_PATH'] = 'results'

def create_app():

    from .views import views
    from .process import process

    app.register_blueprint(views, url_prefix='/')
    app.register_blueprint(process, url_prefix='/')

    return app