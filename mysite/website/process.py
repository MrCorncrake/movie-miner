from flask import Blueprint, render_template, request

import subprocess

JAR_PATH = './jar/movie-miner-1.0-SNAPSHOT-jar-with-dependencies.jar'
TXT_PATH = './jar/transitions.txt'
# PDF_PATH = 'E:\\Diploma_thesis\\movie-miner-master\\scenarios\\RushHour.pdf'
# TXT_PATH = 'E:\\Diploma_thesis\\ms-web-app\\jar\\transitions.txt'
PDF_PATH = './uploads'
RESULT_PATH = 'tmp2.json'
XPDL_RESULT_PATH = 'tmp.xpdl'
PDF_JSON = 'PDF-JSON'
JSON_XPDL = 'JSON-XPDL'
PDF_XPDL = 'PDF-XPDL'


process = Blueprint('process', __name__)

@process.route('/process')
def script_parser(PDF_NAME):
    print("Script Parser processing")
    subprocess.call(['java', '-jar', JAR_PATH, PDF_PATH, TXT_PATH, RESULT_PATH])
    return render_template('home.html')