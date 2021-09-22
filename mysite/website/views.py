from flask import Blueprint, render_template, request, redirect, url_for, send_file, jsonify
import requests

from werkzeug.utils import secure_filename
import os
import sys
from . import app

import subprocess
import datetime

import threading
import random


JAR_PATH = './jar/movie-miner-1.0-SNAPSHOT-jar-with-dependencies.jar'
TXT_PATH = './jar/transitions.txt'
PDF_DIR = './uploads/'
RESULT_DIR = './results/'

views = Blueprint('views', __name__)


class ProcessingThread(threading.Thread):
    def __init__(self):
        self.progress = 0
        self.status = ""
        self.uploaded_file = request.files['file']
        super().__init__()

    def run(self):
        print("Uploading")
        self.status = "Uploading..."
        self.progress = 5
        # uploaded_file = self.uploaded_file
        filename = secure_filename(self.uploaded_file.filename)
        if filename != '':
            file_ext = os.path.splitext(filename)[1]
            if file_ext not in app.config['UPLOAD_EXTENSIONS']:
                self.status = "Invalid file..."
                self.progress = 5
                return "Invalid file", 400
            self.uploaded_file.save(os.path.join(app.config['UPLOAD_PATH'], self.uploaded_file.filename))
            self.status = "Uploaded..."
            self.progress = 45
            file_url = os.path.join(app.config['UPLOAD_PATH'], self.uploaded_file.filename)
            self.status = "Start Parsing..."
            self.progress = 75
            script_parser(file_url)
            self.status = "Parsed..."
            self.progress = 95
            result_name = os.path.basename(file_url)
            result_name = '#'.join(result_name.split('.')[0:-1])
            result_name = result_name + '.json'
            self.status = "Results Saved!"
            self.progress = 100
            return redirect(url_for('views.download_file', json_name=result_name))
        return '', 204

@views.route('/')
def home():

    print(app.config['UPLOAD_PATH'])
    # files = os.listdir(app.config['UPLOAD_PATH'])
    return render_template('home.html')

progressing_threads = {}

@views.route('/progress/')
def progress():
    global progressing_threads

    data = {
        "progress": progressing_threads["progress"],
        "status": progressing_threads["status"]
    }

    return jsonify(data)

@views.route('/', methods=['POST'])
def upload_files():


    # thread_id = random.randint(0, 10000)
    # progressing_threads[thread_id] = ProcessingThread()
    # progressing_threads[thread_id].start()
    #
    # return render_template("processing.html", thread_id=thread_id)

    print("uploading files")
    uploaded_file = request.files['file']
    filename = secure_filename(uploaded_file.filename)
    progressing_threads["progress"] = 10
    progressing_threads["status"] = "Processing"
    if filename != '':
        file_ext = os.path.splitext(filename)[1]
        if file_ext not in app.config['UPLOAD_EXTENSIONS']:
            return "Invalid file", 400
        uploaded_file.save(os.path.join(app.config['UPLOAD_PATH'], filename))
        file_url = os.path.join(app.config['UPLOAD_PATH'], filename)
        script_parser(file_url)
        result_name = os.path.basename(file_url)
        result_name = '#'.join(result_name.split('.')[0:-1])
        result_name = result_name + '.json'
        # return redirect(url_for('views.script_parser',pdf_path=file_url))
        progressing_threads["progress"] = 100
        progressing_threads["status"] = "Done"
        return redirect(url_for('views.download_file', json_name=result_name))
    return '', 204

# @views.route('/process/<pdf_path>')
def script_parser(pdf_path):
    print("parsing files")
    print("Script Parser processing")
    result_name = os.path.basename(pdf_path)
    result_name = '#'.join(result_name.split('.')[0:-1])
    result_name = result_name + '.json'
    print(os.path.basename(pdf_path))
    result_path = os.path.join(sys.path[0],'results', result_name)
    subprocess.call(['java', '-jar', JAR_PATH, pdf_path, TXT_PATH, result_path])
    # return redirect(url_for('views.download_file',json_name=result_name))

@views.route('/results/<json_name>')
def download_file (json_name):
    # return send_from_directory(app.config['DOWNLOAD_PATH'], json_name, as_attachment=True)
    print("downloading files")
    result_path = os.path.join(sys.path[0],'results', json_name)
    print(result_path)

    # return Response(result_path,
    #                  mimetype='text/json',
    #                  as_attachment=True,
    #                  attachment_filename=json_name
    #                  )
    rv = send_file(result_path,
                     mimetype='text/json',
                     as_attachment=True,
                     attachment_filename=json_name,
                     add_etags=False,
                     conditional=True
                     )
    rv.last_modified = datetime.datetime.strptime('2012-11-24 08:51:27', '%Y-%m-%d %H:%M:%S')
    app.logger.debug(rv.last_modified)  # 2012-11-24 08:51:27
    app.logger.debug(request.if_modified_since)  # 2012-11-24 08:51:27
    return rv
@views.errorhandler(413)
def too_large(e):
    return "File is too large", 413

