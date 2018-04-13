from api_app.config.database import DataBase
from api_app.constants.database.targets_table import TargetsTable
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class TargetDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Used to get a list of targets by week
    def GetTargetsByWeek(self, user_id, week_number, year_number):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(TargetsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TargetsTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .And() \
                    .ColumnsNoBrackets([TargetsTable.WEEK]) \
                    .Equals() \
                    .ValuesNoBrackets([week_number]) \
                    .And() \
                    .ColumnsNoBrackets([TargetsTable.YEAR]) \
                    .Equals() \
                    .ValuesNoBrackets([year_number]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([TargetsTable.ID]) \
                    .Asc() \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                targets = []

                for values in cursor.fetchall():
                    targets.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return targets
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Create Target from a target object
    def CreateTarget(self, target):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(TargetsTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        TargetsTable.ATHLETE_ID,
                        TargetsTable.CONTENT,
                        TargetsTable.STATUS,
                        TargetsTable.WEEK,
                        TargetsTable.YEAR
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        target.Athlete_Id.__str__(),
                        target.Content.__str__(),
                        target.Status.__str__(),
                        target.Week.__str__(),
                        target.Year.__str__()
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

    # Update target from a update object
    def UpdateTarget(self, update_data):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(TargetsTable.TABLE_NAME) \
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

    # Used to delete targets
    def DeleteTarget(self, user_id, target_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete() \
                    .From() \
                    .Table(TargetsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([TargetsTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([target_id]) \
                    .And() \
                    .ColumnsNoBrackets([TargetsTable.ATHLETE_ID]) \
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
