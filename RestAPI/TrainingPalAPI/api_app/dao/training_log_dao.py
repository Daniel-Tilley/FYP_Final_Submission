from api_app.constants.database.training_log_table import TrainingLogTable
from api_app.config.database import DataBase
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class TrainingLogDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Used to get a single user
    def GetWorkoutById(self, workout_id, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TrainingLogTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([workout_id]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():
                    workouts = DAOHelper.ConvertResultsToObject(columns, values)

                return workouts[0]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetWorkoutsByDay(self, day, month, year, user_id):

        date_condition = "" + year + "-" + month + "-" + day + ""

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TrainingLogTable.LOG_DATE]) \
                    .Like() \
                    .ValuesNoBrackets([date_condition]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([TrainingLogTable.LOG_DATE]) \
                    .Asc() \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                workouts = []

                for values in cursor.fetchall():
                    workouts.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return workouts
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    #  Used to get a list of users
    def GetWorkoutsByWeek(self, week, year, user_id, log_type):

        date_condition = "" + year + "" + week + ""

        try:
            with self.db.cursor() as cursor:

                base_sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .Where() \
                    .FunctionColumns("YEARWEEK", [TrainingLogTable.LOG_DATE]) \
                    .Like() \
                    .ValuesNoBrackets([date_condition]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                if log_type:
                    new_sql = QueryBuilder() \
                        .ExtendQuery(base_sql) \
                        .And() \
                        .ColumnsNoBrackets([TrainingLogTable.TYPE_ID]) \
                        .Equals() \
                        .ValuesNoBrackets(log_type) \
                        .Build()

                    sql = new_sql

                else:
                    sql = base_sql

                final_sql = QueryBuilder()\
                    .ExtendQuery(sql)\
                    .OrderBy()\
                    .ColumnsNoBrackets([TrainingLogTable.LOG_DATE])\
                    .Asc()\
                    .Build()

                cursor.execute(final_sql)
                columns = cursor.description

                workouts = []

                for values in cursor.fetchall():
                    workouts.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return workouts
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetWorkoutsByMonth(self, month, month_end, year, user_id):

        date_start_condition = "" + year + "-" + month + "-01"
        date_end_condition = "" + year + "-" + month + "-" + str(month_end) + ""

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TrainingLogTable.LOG_DATE]) \
                    .Between() \
                    .ValuesNoBrackets([date_start_condition]) \
                    .And() \
                    .ValuesNoBrackets([date_end_condition]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([TrainingLogTable.LOG_DATE]) \
                    .Asc() \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                workouts = []

                for values in cursor.fetchall():
                    workouts.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return workouts
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Create User from a user object
    def CreateWorkout(self, training_log):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        TrainingLogTable.ATHLETE_ID,
                        TrainingLogTable.TYPE_ID,
                        TrainingLogTable.LOG_DATE,
                        TrainingLogTable.LOG_TIME,
                        TrainingLogTable.LOG_NAME,
                        TrainingLogTable.LOG_DESC,
                        TrainingLogTable.ATHLETES_COMMENTS,
                        TrainingLogTable.COACHES_COMMENTS,
                        TrainingLogTable.WORKOUT_COMMENTS,
                        TrainingLogTable.DURATION_PLANNED,
                        TrainingLogTable.DURATION_ACTUAL,
                        TrainingLogTable.DISTANCE_PLANNED,
                        TrainingLogTable.DISTANCE_ACTUAL,
                        TrainingLogTable.DISTANCE_UNIT,
                        TrainingLogTable.HR_RESTING_PLANNED,
                        TrainingLogTable.HR_AVG_PLANNED,
                        TrainingLogTable.HR_MAX_PLANNED,
                        TrainingLogTable.HR_RESTING_ACTUAL,
                        TrainingLogTable.HR_AVG_ACTUAL,
                        TrainingLogTable.HR_MAX_ACTUAL,
                        TrainingLogTable.WATTS_AVG_PLANNED,
                        TrainingLogTable.WATTS_MAX_PLANNED,
                        TrainingLogTable.WATTS_AVG_ACTUAL,
                        TrainingLogTable.WATTS_MAX_ACTUAL,
                        TrainingLogTable.RPE_PLANNED,
                        TrainingLogTable.RPE_ACTUAL,
                        TrainingLogTable.HR_ZONE1_TIME,
                        TrainingLogTable.HR_ZONE2_TIME,
                        TrainingLogTable.HR_ZONE3_TIME,
                        TrainingLogTable.HR_ZONE4_TIME,
                        TrainingLogTable.HR_ZONE5_TIME,
                        TrainingLogTable.HR_ZONE6_TIME,
                        TrainingLogTable.CALORIES_BURNED,
                        TrainingLogTable.SLEEP_QUALITY
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        training_log.Athlete_Id.__str__(),
                        training_log.Type_ID.__str__(),
                        training_log.Log_Date.__str__(),
                        training_log.Log_Time.__str__(),
                        training_log.Log_Name.__str__(),
                        training_log.Log_Description.__str__(),
                        training_log.Athletes_Comments.__str__(),
                        training_log.Coaches_Comments.__str__(),
                        training_log.Workout_Comments.__str__(),
                        training_log.Duration_Planned.__str__(),
                        training_log.Duration_Actual.__str__(),
                        training_log.Distance_Planned.__str__(),
                        training_log.Distance_Actual.__str__(),
                        training_log.Distance_Unit.__str__(),
                        training_log.HR_Resting_Planned.__str__(),
                        training_log.HR_Avg_Planned.__str__(),
                        training_log.HR_Max_Planned.__str__(),
                        training_log.HR_Resting_Actual.__str__(),
                        training_log.HR_Avg_Actual.__str__(),
                        training_log.HR_Max_Actual.__str__(),
                        training_log.Watts_Avg_Planned.__str__(),
                        training_log.Watts_Max_Planned.__str__(),
                        training_log.Watts_Avg_Actual.__str__(),
                        training_log.Watts_Max_Actual.__str__(),
                        training_log.RPE_Planned.__str__(),
                        training_log.RPE_Actual.__str__(),
                        training_log.HR_Zone1_Time.__str__(),
                        training_log.HR_Zone2_Time.__str__(),
                        training_log.HR_Zone3_Time.__str__(),
                        training_log.HR_Zone4_Time.__str__(),
                        training_log.HR_Zone5_Time.__str__(),
                        training_log.HR_Zone6_Time.__str__(),
                        training_log.Calories_Burned.__str__(),
                        training_log.Sleep_Quality.__str__()
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
    def UpdateWorkout(self, update_data):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(TrainingLogTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues(update_data.memberKeys, update_data.memberValues) \
                    .Where() \
                    .ColumnsNoBrackets([update_data.identifierKeys[0]]) \
                    .Like() \
                    .ValuesNoBrackets([update_data.identifierValues[0]]) \
                    .And() \
                    .ColumnsNoBrackets([update_data.identifierKeys[1]]) \
                    .Like() \
                    .ValuesNoBrackets([update_data.identifierValues[1]]) \
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

    # Used to delete workouts
    def DeleteWorkout(self, user_id, workout_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete()\
                    .From()\
                    .Table(TrainingLogTable.TABLE_NAME)\
                    .Where()\
                    .ColumnsNoBrackets([TrainingLogTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([workout_id]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                cursor.execute(sql)

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
