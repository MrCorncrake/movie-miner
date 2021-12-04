from flask import Blueprint, render_template


aboutus = Blueprint('aboutus', __name__)

@aboutus.route('/')
def abc():
    return render_template('aboutus.html')