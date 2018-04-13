from api_app.config.database import DataBase
from api_app.constants.database.invites_table import InvitesTable
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class InviteDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # Used to get a list of invites
    def GetInvites(self, user_id, invite_status_type, invite_type=None):

        try:
            with self.db.cursor() as cursor:

                if invite_type is not None:
                    if invite_status_type == "sent":

                        sql = QueryBuilder() \
                            .Select() \
                            .AllColumns() \
                            .From() \
                            .Table(InvitesTable.TABLE_NAME) \
                            .Where() \
                            .ColumnsNoBrackets([InvitesTable.SENT_BY]) \
                            .Like() \
                            .ValuesNoBrackets([user_id]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.INVITE_TYPE]) \
                            .Like() \
                            .ValuesNoBrackets([invite_type]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .In() \
                            .ValuesBrackets([1, 2]) \
                            .OrderBy() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .Asc() \
                            .Comma() \
                            .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                            .Desc() \
                            .Build()

                    else:

                        sql = QueryBuilder() \
                            .Select() \
                            .AllColumns() \
                            .From() \
                            .Table(InvitesTable.TABLE_NAME) \
                            .Where() \
                            .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                            .Like() \
                            .ValuesNoBrackets([user_id]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.INVITE_TYPE]) \
                            .Like() \
                            .ValuesNoBrackets([invite_type]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .In() \
                            .ValuesBrackets([1, 2]) \
                            .OrderBy() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .Asc() \
                            .Comma() \
                            .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                            .Desc() \
                            .Build()

                else:
                    if invite_status_type == "sent":

                        sql = QueryBuilder() \
                            .Select() \
                            .AllColumns() \
                            .From() \
                            .Table(InvitesTable.TABLE_NAME) \
                            .Where() \
                            .ColumnsNoBrackets([InvitesTable.SENT_BY]) \
                            .Like() \
                            .ValuesNoBrackets([user_id]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .In() \
                            .ValuesBrackets([1, 2]) \
                            .OrderBy() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .Asc() \
                            .Comma() \
                            .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                            .Desc() \
                            .Build()

                    else:

                        sql = QueryBuilder() \
                            .Select() \
                            .AllColumns() \
                            .From() \
                            .Table(InvitesTable.TABLE_NAME) \
                            .Where() \
                            .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                            .Like() \
                            .ValuesNoBrackets([user_id]) \
                            .And() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .In() \
                            .ValuesBrackets([1, 2]) \
                            .OrderBy() \
                            .ColumnsNoBrackets([InvitesTable.STATUS]) \
                            .Asc() \
                            .Comma() \
                            .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                            .Desc() \
                            .Build()

                cursor.execute(sql)
                columns = cursor.description

                invites = []

                for values in cursor.fetchall():
                    invites.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return invites
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Used to get a single invite
    def GetInvite(self, sent_by, sent_to, status, invite_type, send_date):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.SENT_BY]) \
                    .Like() \
                    .ValuesNoBrackets([sent_by]) \
                    .And() \
                    .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                    .Like() \
                    .ValuesNoBrackets([sent_to]) \
                    .And() \
                    .ColumnsNoBrackets([InvitesTable.STATUS]) \
                    .Like() \
                    .ValuesNoBrackets([status]) \
                    .And() \
                    .ColumnsNoBrackets([InvitesTable.INVITE_TYPE]) \
                    .Like() \
                    .ValuesNoBrackets([invite_type]) \
                    .And() \
                    .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                    .Like() \
                    .ValuesNoBrackets([send_date]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():
                    invites = DAOHelper.ConvertResultsToObject(columns, values)

                return invites[0]

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Return count of new invites
    def GetNewInvitesCount(self, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .And() \
                    .ColumnsNoBrackets([InvitesTable.STATUS]) \
                    .Like() \
                    .ValuesNoBrackets([1]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([InvitesTable.SEND_DATE]) \
                    .Asc() \
                    .Build()

                cursor.execute(sql, QueryBuilder().CountNulls(sql).BuildNullTuple())

                return cursor.rowcount

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # Create Invite from a invite object
    def CreateSingleInvite(self, invite):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        InvitesTable.INVITE_TYPE,
                        InvitesTable.SENT_BY,
                        InvitesTable.SENT_TO,
                        InvitesTable.STATUS,
                        InvitesTable.SEND_DATE,
                        InvitesTable.EVENT_ID
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        invite.Invite_Type.__str__(),
                        invite.Sent_By.__str__(),
                        invite.Sent_To.__str__(),
                        invite.Status.__str__(),
                        invite.Send_Date.__str__(),
                        invite.Event_Id.__str__()
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

    # Create Invite from a invite object
    def CreateMultipleInvites(self, invite, recipients):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        InvitesTable.INVITE_TYPE,
                        InvitesTable.SENT_BY,
                        InvitesTable.SENT_TO,
                        InvitesTable.STATUS,
                        InvitesTable.SEND_DATE,
                        InvitesTable.EVENT_ID
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        "%s", "%s", "%s", "%s", "%s", "%s"
                    ]) \
                    .Build()

                invites = []

                for recipient in recipients:

                    invite_tuple = (
                        invite.Invite_Type,
                        invite.Sent_By.__str__(),
                        recipient,
                        invite.Status.__str__(),
                        invite.Send_Date.__str__(),
                        invite.Event_Id.__str__() if invite.Event_Id is not None else None
                    )

                    invites.append(invite_tuple)

                cursor.executemany(sql, invites)

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

    # Update invite from a update object
    def UpdateSingleInvite(self, update_data):
        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues(update_data.memberKeys, update_data.memberValues) \
                    .Where() \
                    .ColumnsNoBrackets([update_data.identifierKeys[0]]) \
                    .Like() \
                    .ValuesNoBrackets([update_data.identifierValues[0]]) \
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

    # Create Invite from a invite object
    def UpdateMultipleInvites(self, update_data):

        try:
            with self.db.cursor() as cursor:

                values = []

                for item in update_data.memberValues:
                    values.append("%s")

                sql = QueryBuilder() \
                    .Update() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues(update_data.memberKeys, values) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets(["%s"]) \
                    .Build()

                invites = []

                for id in update_data.inviteIds:
                    base_tuple = (update_data.memberValues[0],)

                    for i in range(len(update_data.memberValues)):

                        if i != 0:
                            base_tuple += (update_data.memberValues[i],)

                    base_tuple += (id,)

                    invites.append(base_tuple)

                cursor.executemany(sql, invites)

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

    # Used to delete invites
    def DeclineInvite(self, invite_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Set() \
                    .UpdateValues([InvitesTable.STATUS], [3]) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([invite_id]) \
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

    # DeleteInvitesFromEvent
    def DeleteInvitesFromEvent(self, event_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete() \
                    .From() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.EVENT_ID]) \
                    .Like() \
                    .ValuesNoBrackets([event_id]) \
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

    # Check if current user can edit or delete the invite
    def CanEditOrDelete(self, user_id, invite_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([InvitesTable.ID, InvitesTable.SENT_BY, InvitesTable.SENT_TO]) \
                    .From() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([invite_id]) \
                    .And() \
                    .LeftRoundedBracket() \
                    .ColumnsNoBrackets([InvitesTable.SENT_BY]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Or() \
                    .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .RightRoundedBracket() \
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

    def CanEditOrDeleteMultiple(self, user_id, ids):

        try:
            with self.db.cursor() as cursor:

                ids_count = len(ids)

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([InvitesTable.ID, InvitesTable.SENT_BY, InvitesTable.SENT_TO]) \
                    .From() \
                    .Table(InvitesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([InvitesTable.ID]) \
                    .In() \
                    .ValuesBrackets(ids) \
                    .And() \
                    .LeftRoundedBracket() \
                    .ColumnsNoBrackets([InvitesTable.SENT_BY]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Or() \
                    .ColumnsNoBrackets([InvitesTable.SENT_TO]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .RightRoundedBracket() \
                    .Build()

                cursor.execute(sql)

                if cursor.rowcount == ids_count:
                    return True
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()
