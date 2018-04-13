from pymysql import connect


class DataBase:

    def __init__(self):
        pass

    @staticmethod
    def GetDataBaseConnection():
        try:
            return connect(
                host="localhost",
                user="TrainingPalAPIUser",
                password="Bcb5qzStuQ8nBRLfceazfyM3tg5x",
                db="TrainingPal"
            )

        except:
            return None
