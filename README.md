## How to run the project

Python version: 3.8 64bit

To install virtualenv run
```
pip install virtualenv
```

To create virtual environment run
```
virtualenv venv
```

To activate virtualenv run aproperiate script:

 * On linux:
   ```
   .\venv\Scripts\activate
   ```
 * On Windows Terminal:
   ```
   .\venv\Scripts\activate.bat
   ```
 * On Windows Power Shell (note that you might need to enable execution of scripts first):
   ```
   .\venv\Scripts\activate.bat
   ```

To install required packages in activated virtualenv run
```
pip install -r requirements.txt
```

To start the application run
```
python application.py
```