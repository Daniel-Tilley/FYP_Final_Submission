from api_app.config.database import DataBase
from api_app.constants.database.invites_table import InvitesTable
from api_app.dao.invite_dao import InviteDao
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder
from api_app.constants.database.coach_athlete_table import CoachAthleteTable
from api_app.models.invite import Invite


class AccessDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Used to user has access to another user
    def CheckAccess(self, coach_id, athlete_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([athlete_id]) \
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

    # Used to check if coach has access to athlete training logs
    def CheckCoachTrainingLogAccess(self, coach_id, athlete_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([athlete_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.IS_ACTIVE]) \
                    .Like() \
                    .ValuesNoBrackets(["1"]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.CAN_ACCESS_TRAINING_LOG]) \
                    .Like() \
                    .ValuesNoBrackets(["1"]) \
                    .Build()

                cursor.execute(sql)

                if cursor.rowcount != 0:
                    return True
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to check if coach has access to athlete targets
    def CheckCoachTargetsAccess(self, coach_id, athlete_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([athlete_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.IS_ACTIVE]) \
                    .Like() \
                    .ValuesNoBrackets(["1"]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.CAN_ACCESS_TARGETS]) \
                    .Like() \
                    .ValuesNoBrackets(["1"]) \
                    .Build()

                cursor.execute(sql)

                if cursor.rowcount != 0:
                    return True
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Update Coaches Access
    def UpdateCoachAccess(self, user_id, coach_id, access):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues([
                        CoachAthleteTable.CAN_ACCESS_TARGETS,
                        CoachAthleteTable.CAN_ACCESS_TRAINING_LOG
                    ], [
                        access.Can_Access_Targets.__str__(),
                        access.Can_Access_Training_Log.__str__()
                    ]) \
                    .Where() \
                    .ColumnsNoBrackets([CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
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

    # Used for updating access after an invite has been accepted
    def UpdateFromInvite(self, update_data):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
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

    # Delete Coach Access
    def RevokeCoachAccess(self, athlete_id, coach_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete() \
                    .From() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([CoachAthleteTable.ATHLETE_ID]) \
                    .Like() \
                    .ValuesNoBrackets([athlete_id]) \
                    .And() \
                    .ColumnsNoBrackets([CoachAthleteTable.COACH_ID]) \
                    .Like() \
                    .ValuesNoBrackets([coach_id]) \
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

    def CreateAccess(self, access):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        CoachAthleteTable.ATHLETE_ID,
                        CoachAthleteTable.COACH_ID,
                        CoachAthleteTable.CAN_ACCESS_TRAINING_LOG,
                        CoachAthleteTable.CAN_ACCESS_TARGETS,
                        CoachAthleteTable.IS_ACTIVE,
                        CoachAthleteTable.START_DATE,
                        CoachAthleteTable.INVITE_ID,
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        access.Athlete_Id.__str__(),
                        access.Coach_Id.__str__(),
                        access.Can_Access_Training_Log.__str__(),
                        access.Can_Access_Targets.__str__(),
                        access.Is_Active.__str__(),
                        access.Start_Date.__str__(),
                        access.Invite_Id.__str__()
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

    # Requests Coach Access and creates and invite
    def CreateCoachAccessWithInvite(self, access):

        create_invite_dao = InviteDao()

        invite = Invite(
            None,  # For Id that is auto generated by a trigger
            3,
            access.Coach_Id.__str__(),
            access.Athlete_Id.__str__(),
            1,
            access.Start_Date.__str__(),
            None
        )

        if create_invite_dao.CreateSingleInvite(invite):

            get_invite_dao = InviteDao()

            invite_confirm = get_invite_dao.GetInvite(
                invite.Sent_By,
                invite.Sent_To,
                invite.Status,
                invite.Invite_Type,
                invite.Send_Date
            )

            if invite_confirm:

                cursor = self.db.cursor()

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(CoachAthleteTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        CoachAthleteTable.ATHLETE_ID,
                        CoachAthleteTable.COACH_ID,
                        CoachAthleteTable.CAN_ACCESS_TRAINING_LOG,
                        CoachAthleteTable.CAN_ACCESS_TARGETS,
                        CoachAthleteTable.IS_ACTIVE,
                        CoachAthleteTable.START_DATE,
                        CoachAthleteTable.INVITE_ID,
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        access.Athlete_Id.__str__(),
                        access.Coach_Id.__str__(),
                        access.Can_Access_Training_Log.__str__(),
                        access.Can_Access_Targets.__str__(),
                        access.Is_Active.__str__(),
                        access.Start_Date.__str__(),
                        invite_confirm[str(InvitesTable.ID)]
                    ]) \
                    .Build()

                cursor.execute(sql, QueryBuilder().CountNulls(sql).BuildNullTuple())

                if cursor.rowcount != 0:
                    self.db.commit()
                    cursor.close()
                    self.db.close()
                    return True
                else:
                    cursor.close()
                    self.db.close()
                    return None

            else:
                return None

        else:
            return None
