import subprocess

JAR_PATH = './jar/movie-miner-1.0-SNAPSHOT-jar-with-dependencies.jar'
PDF_PATH = 'E:\\Diploma_thesis\\movie-miner-master\\scenarios\\RushHour.pdf'
TXT_PATH = 'E:\\Diploma_thesis\\ms-web-app\\jar\\transitions.txt'
RESULT_PATH = 'tmp2.json'
# E:\Diploma_thesis\movie-miner-master\scenarios\RushHour.pdf
# E:\Diploma_thesis\ms-web-app\jar\transitions.txt


if __name__ == '__main__':
    print("Hi")
    subprocess.call(['java', '-jar', JAR_PATH, PDF_PATH, TXT_PATH, RESULT_PATH])