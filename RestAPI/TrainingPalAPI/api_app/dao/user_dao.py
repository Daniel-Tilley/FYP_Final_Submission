from api_app.constants.database.users_table import UsersTable
from api_app.constants.database.coach_athlete_table import CoachAthleteTable
from api_app.config.database import DataBase
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class UserDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Get a users password hash
    def GetUserPasswordHash(self, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([UsersTable.PASSWORD]) \
                    .From() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([UsersTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():

                    users = DAOHelper.ConvertResultsToObject(columns, values)

                return users[0]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a single user
    def GetUserById(self, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([UsersTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():

                    users = DAOHelper.ConvertResultsToObject(columns, values)

                return users[0]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetUsers(self, search_type, search_query):
        profile_type = ""

        if search_type == "athlete":
            profile_type = "ATH"

        if search_type == "coach":
            profile_type = "COA"

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([
                        UsersTable.ID,
                        UsersTable.F_NAME,
                        UsersTable.L_NAME,
                        UsersTable.LOCATION,
                    ]) \
                    .From() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([UsersTable.TYPE]) \
                    .Like() \
                    .ValuesNoBrackets([profile_type]) \
                    .And() \
                    .LeftRoundedBracket() \
                    .ColumnsNoBrackets([UsersTable.ID]) \
                    .Like() \
                    .QueryValue(search_query) \
                    .Or() \
                    .ColumnsNoBrackets([UsersTable.F_NAME]) \
                    .Like() \
                    .QueryValue(search_query) \
                    .Or() \
                    .ColumnsNoBrackets([UsersTable.L_NAME]) \
                    .Like() \
                    .QueryValue(search_query) \
                    .Or() \
                    .ColumnsNoBrackets([UsersTable.LOCATION]) \
                    .Like() \
                    .QueryValue(search_query) \
                    .RightRoundedBracket() \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                users = []

                for values in cursor.fetchall():
                    users.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return users

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetCoachAthletes(self, coach_id):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .TableColumnsNoBrackets(UsersTable.TABLE_NAME, [
                        UsersTable.ID,
                        UsersTable.F_NAME,
                        UsersTable.L_NAME,
                        UsersTable.LOCATION
                    ]) \
                    .Comma() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [
                        CoachAthleteTable.CAN_ACCESS_TARGETS,
                        CoachAthleteTable.CAN_ACCESS_TRAINING_LOG
                    ]) \
                    .From() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Join() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .On() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.ATHLETE_ID]) \
                    .Equals() \
                    .TableColumnsNoBrackets(UsersTable.TABLE_NAME, [UsersTable.ID]) \
                    .Where() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
                    .And() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.IS_ACTIVE]) \
                    .Equals() \
                    .ValuesNoBrackets(["1"]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                users = []

                for values in cursor.fetchall():
                    users.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return users

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetAthleteCoaches(self, athlete_id):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .TableColumnsNoBrackets(UsersTable.TABLE_NAME, [
                        UsersTable.ID,
                        UsersTable.F_NAME,
                        UsersTable.L_NAME,
                        UsersTable.LOCATION
                    ]) \
                    .Comma() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [
                        CoachAthleteTable.CAN_ACCESS_TARGETS,
                        CoachAthleteTable.CAN_ACCESS_TRAINING_LOG
                    ]) \
                    .From() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Join() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .On() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.COACH_ID]) \
                    .Equals() \
                    .TableColumnsNoBrackets(UsersTable.TABLE_NAME, [UsersTable.ID]) \
                    .Where() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([athlete_id]) \
                    .And() \
                    .TableColumnsNoBrackets(CoachAthleteTable.TABLE_NAME, [CoachAthleteTable.IS_ACTIVE]) \
                    .Equals() \
                    .ValuesNoBrackets(["1"]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                users = []

                for values in cursor.fetchall():
                    users.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return users

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Create User from a user object
    def CreateUser(self, user):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(UsersTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        UsersTable.ID,
                        UsersTable.PASSWORD,
                        UsersTable.F_NAME,
                        UsersTable.L_NAME,
                        UsersTable.E_MAIL,
                        UsersTable.DOB,
                        UsersTable.TYPE,
                        UsersTable.LOCATION,
                        UsersTable.BIO
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        user.Id.__str__(),
                        user.Password.__str__(),
                        user.F_Name.__str__(),
                        user.L_Name.__str__(),
                        user.Email.__str__(),
                        user.DOB.__str__(),
                        user.Type.__str__(),
                        user.Location.__str__(),
                        user.Bio.__str__()
                    ]) \
                    .Build()

                cursor.execute(sql, QueryBuilder().CountNulls(sql).BuildNullTuple())

                if cursor.rowcount != 0:
                    self.db.commit()
                    return True
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Update User from a user object
    def UpdateUser(self, update_data):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(UsersTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues(update_data.memberKeys, update_data.memberValues)\
                    .Where() \
                    .ColumnsNoBrackets(update_data.identifierKeys) \
                    .Like() \
                    .ValuesNoBrackets(update_data.identifierValues) \
                    .Build()

                cursor.execute(sql, QueryBuilder().CountNulls(sql).BuildNullTuple())

                if cursor.rowcount != 0:
                    self.db.commit()
                    return True
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()
