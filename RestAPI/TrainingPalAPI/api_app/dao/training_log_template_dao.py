from api_app.constants.database.training_log_template_table import TrainingLogTemplateTable
from api_app.config.database import DataBase
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class TrainingLogTemplateDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Used to get a single user
    def GetTemplateById(self, template_id, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TrainingLogTemplateTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TrainingLogTemplateTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([template_id]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTemplateTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():
                    templates = DAOHelper.ConvertResultsToObject(columns, values)

                return templates[0]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a list of users
    def GetTemplates(self, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([
                        TrainingLogTemplateTable.COACH_ID,
                        TrainingLogTemplateTable.ID,
                        TrainingLogTemplateTable.NAME
                    ]) \
                    .From() \
                    .Table(TrainingLogTemplateTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TrainingLogTemplateTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([TrainingLogTemplateTable.NAME]) \
                    .Asc() \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                templates = []

                for values in cursor.fetchall():
                    templates.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return templates
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Create User from a user object
    def CreateTrainingLogTemplate(self, training_log_template):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(TrainingLogTemplateTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        TrainingLogTemplateTable.COACH_ID,
                        TrainingLogTemplateTable.NAME,
                        TrainingLogTemplateTable.DURATION_PLANNED,
                        TrainingLogTemplateTable.DURATION_ACTUAL,
                        TrainingLogTemplateTable.DISTANCE_PLANNED,
                        TrainingLogTemplateTable.DISTANCE_ACTUAL,
                        TrainingLogTemplateTable.DISTANCE_UNIT,
                        TrainingLogTemplateTable.HR_RESTING_PLANNED,
                        TrainingLogTemplateTable.HR_AVG_PLANNED,
                        TrainingLogTemplateTable.HR_MAX_PLANNED,
                        TrainingLogTemplateTable.HR_RESTING_ACTUAL,
                        TrainingLogTemplateTable.HR_AVG_ACTUAL,
                        TrainingLogTemplateTable.HR_MAX_ACTUAL,
                        TrainingLogTemplateTable.WATTS_AVG_PLANNED,
                        TrainingLogTemplateTable.WATTS_MAX_PLANNED,
                        TrainingLogTemplateTable.WATTS_AVG_ACTUAL,
                        TrainingLogTemplateTable.WATTS_MAX_ACTUAL,
                        TrainingLogTemplateTable.RPE_PLANNED,
                        TrainingLogTemplateTable.RPE_ACTUAL,
                        TrainingLogTemplateTable.HR_ZONE1_TIME,
                        TrainingLogTemplateTable.HR_ZONE2_TIME,
                        TrainingLogTemplateTable.HR_ZONE3_TIME,
                        TrainingLogTemplateTable.HR_ZONE4_TIME,
                        TrainingLogTemplateTable.HR_ZONE5_TIME,
                        TrainingLogTemplateTable.HR_ZONE6_TIME
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        training_log_template.Coach_Id.__str__(),
                        training_log_template.Name.__str__(),
                        training_log_template.Duration_Planned.__str__(),
                        training_log_template.Duration_Actual.__str__(),
                        training_log_template.Distance_Planned.__str__(),
                        training_log_template.Distance_Actual.__str__(),
                        training_log_template.Distance_Unit.__str__(),
                        training_log_template.HR_Resting_Planned.__str__(),
                        training_log_template.HR_Avg_Planned.__str__(),
                        training_log_template.HR_Max_Planned.__str__(),
                        training_log_template.HR_Resting_Actual.__str__(),
                        training_log_template.HR_Avg_Actual.__str__(),
                        training_log_template.HR_Max_Actual.__str__(),
                        training_log_template.Watts_Avg_Planned.__str__(),
                        training_log_template.Watts_Max_Planned.__str__(),
                        training_log_template.Watts_Avg_Actual.__str__(),
                        training_log_template.Watts_Max_Actual.__str__(),
                        training_log_template.RPE_Planned.__str__(),
                        training_log_template.RPE_Actual.__str__(),
                        training_log_template.HR_Zone1_Time.__str__(),
                        training_log_template.HR_Zone2_Time.__str__(),
                        training_log_template.HR_Zone3_Time.__str__(),
                        training_log_template.HR_Zone4_Time.__str__(),
                        training_log_template.HR_Zone5_Time.__str__(),
                        training_log_template.HR_Zone6_Time.__str__()
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
    def UpdateTrainingLogTemplate(self, update_data):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(TrainingLogTemplateTable.TABLE_NAME) \
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
    def DeleteTrainingLogTemplate(self, user_id, template_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete()\
                    .From()\
                    .Table(TrainingLogTemplateTable.TABLE_NAME)\
                    .Where()\
                    .ColumnsNoBrackets([TrainingLogTemplateTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([template_id]) \
                    .And() \
                    .ColumnsNoBrackets([TrainingLogTemplateTable.COACH_ID]) \
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
