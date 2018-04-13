from api_app.config.database import DataBase
from api_app.constants.database.event_attendees_table import EventAttendeesTable
from api_app.constants.database.events_table import EventsTable
from api_app.helpers.dao_helper import DAOHelper
from api_app.helpers.query_builder import QueryBuilder


class EventDao:
    def __init__(self):
        self.db = DataBase.GetDataBaseConnection()

    # CreateEvent
    def CreateEvent(self, event):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(EventsTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        EventsTable.TYPE,
                        EventsTable.NAME,
                        EventsTable.HOST_USERNAME,
                        EventsTable.CREATED_DATE,
                        EventsTable.EVENT_DATE
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        event.Type.__str__(),
                        event.Name.__str__(),
                        event.Host_Username.__str__(),
                        event.Created_Date.__str__(),
                        event.Event_Date.__str__()
                    ]) \
                    .Build()

                cursor.execute(sql, QueryBuilder().CountNulls(sql).BuildNullTuple())

                if cursor.rowcount != 0:
                    self.db.commit()

                    eventDao = EventDao()

                    return eventDao.GetEventId(event)
                else:
                    return None

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # DeleteEvent
    def DeleteEvent(self, event_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete() \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventsTable.ID]) \
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

    # UpdateEvent
    def UpdateEvent(self, update_data):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(EventsTable.TABLE_NAME) \
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

    # GetHostedEvents
    def GetHostedEvents(self, host_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventsTable.HOST_USERNAME]) \
                    .Like() \
                    .ValuesNoBrackets([host_id]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([EventsTable.EVENT_DATE]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                events = []

                for values in cursor.fetchall():

                    events.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                for event in events:

                    event_dao = EventDao()
                    event["Attendees"] = event_dao.GetParticipants(event[EventsTable.ID])

                return events
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # GetParticipatingEvents
    def GetParticipatingEvents(self, participant_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([EventAttendeesTable.ID]) \
                    .From() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventAttendeesTable.USER_ID]) \
                    .Like() \
                    .ValuesNoBrackets([participant_id]) \
                    .Build()

                cursor.execute(sql)

                event_ids = []

                for values in cursor.fetchall():
                    event_ids.append(values[0])

                event_dao = EventDao()

                return event_dao.GetEvents(event_ids)

        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # GetEventId
    def GetEventId(self, event):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([EventsTable.ID]) \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventsTable.TYPE]) \
                    .Like() \
                    .ValuesNoBrackets([event.Type.__str__()]) \
                    .And() \
                    .ColumnsNoBrackets([EventsTable.NAME]) \
                    .Like() \
                    .ValuesNoBrackets([event.Name.__str__()]) \
                    .And() \
                    .ColumnsNoBrackets([EventsTable.HOST_USERNAME]) \
                    .Like() \
                    .ValuesNoBrackets([event.Host_Username.__str__()]) \
                    .And() \
                    .ColumnsNoBrackets([EventsTable.CREATED_DATE]) \
                    .Like() \
                    .ValuesNoBrackets([event.Created_Date.__str__()]) \
                    .And() \
                    .ColumnsNoBrackets([EventsTable.EVENT_DATE]) \
                    .Like() \
                    .ValuesNoBrackets([event.Event_Date.__str__()]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():

                    events = DAOHelper.ConvertResultsToObject(columns, values)

                return events[0][str(EventsTable.ID)]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # GetEvent
    def GetEvent(self, event_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventsTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([event_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                for values in cursor.fetchall():

                    events = DAOHelper.ConvertResultsToObject(columns, values)

                event_dao = EventDao()

                events[0]["Attendees"] = event_dao.GetParticipants(event_id)

                return events[0]
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # GetEvents
    def GetEvents(self, event_ids):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .TableAllColumns(EventsTable.TABLE_NAME) \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Join() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .On() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.ID]) \
                    .Equals() \
                    .TableColumnsNoBrackets(EventAttendeesTable.TABLE_NAME, [EventAttendeesTable.ID]) \
                    .Where() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.ID]) \
                    .In() \
                    .ValuesBrackets(event_ids) \
                    .And() \
                    .TableColumnsNoBrackets(EventAttendeesTable.TABLE_NAME, [EventAttendeesTable.ACCEPTED]) \
                    .Equals() \
                    .ValuesNoBrackets([1]) \
                    .OrderBy() \
                    .ColumnsNoBrackets([EventsTable.EVENT_DATE]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                events = []

                for values in cursor.fetchall():

                    events.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                for event in events:

                    event_dao = EventDao()
                    event["Attendees"] = event_dao.GetParticipants(event[EventsTable.ID])

                return events
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

    # CreateParticipants
    def CreateParticipants(self, event_id, user_ids):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Insert() \
                    .Into() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .ColumnsBrackets([
                        EventAttendeesTable.ID,
                        EventAttendeesTable.USER_ID,
                        EventAttendeesTable.ACCEPTED
                    ]) \
                    .ValuesKeyword() \
                    .ValuesBrackets([
                        "%s", "%s", "%s"
                    ]) \
                    .Build()

                users = []

                for user in user_ids:

                    user_tuple = (
                        event_id,
                        user,
                        0
                    )

                    users.append(user_tuple)

                cursor.executemany(sql, users)

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

    # Accept Participant
    def AcceptParticipant(self, event_id, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Update() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .Set() \
                    .ColumnsNoBrackets([EventAttendeesTable.ACCEPTED]) \
                    .Equals() \
                    .ValuesNoBrackets([1]) \
                    .Where() \
                    .ColumnsNoBrackets([EventAttendeesTable.ID])\
                    .Like()\
                    .ValuesNoBrackets([event_id])\
                    .And()\
                    .ColumnsNoBrackets([EventAttendeesTable.USER_ID])\
                    .Like()\
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

    # RemoveParticipants
    def RemoveParticipants(self, event_id, user_ids):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Delete() \
                    .From() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventAttendeesTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets(["%s"]) \
                    .And() \
                    .ColumnsNoBrackets([EventAttendeesTable.USER_ID]) \
                    .Like() \
                    .ValuesNoBrackets(["%s"]) \
                    .Build()

                users = []

                for user in user_ids:

                    user_tuple = (
                        event_id,
                        user
                    )

                    users.append(user_tuple)

                cursor.executemany(sql, users)

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

    # GetParticipants
    def GetParticipants(self, event_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .ColumnsNoBrackets([EventAttendeesTable.USER_ID, EventAttendeesTable.ACCEPTED]) \
                    .From() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventAttendeesTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([event_id]) \
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

    # CanAccess
    def CanAccess(self, event_id, user_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.ID, EventsTable.HOST_USERNAME]) \
                    .Comma() \
                    .TableColumnsNoBrackets(EventAttendeesTable.TABLE_NAME, [EventAttendeesTable.USER_ID]) \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Join() \
                    .Table(EventAttendeesTable.TABLE_NAME) \
                    .On() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.ID]) \
                    .Equals() \
                    .TableColumnsNoBrackets(EventAttendeesTable.TABLE_NAME, [EventAttendeesTable.ID]) \
                    .Where() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([event_id]) \
                    .And() \
                    .LeftRoundedBracket() \
                    .TableColumnsNoBrackets(EventsTable.TABLE_NAME, [EventsTable.HOST_USERNAME]) \
                    .Like() \
                    .ValuesNoBrackets([user_id]) \
                    .Or() \
                    .TableColumnsNoBrackets(EventAttendeesTable.TABLE_NAME, [EventAttendeesTable.USER_ID]) \
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

    # GetHostedEvents
    def CheckHostAccess(self, host_id, event_id):

        try:
            with self.db.cursor() as cursor:

                sql = QueryBuilder() \
                    .Select() \
                    .AllColumns() \
                    .From() \
                    .Table(EventsTable.TABLE_NAME) \
                    .Where() \
                    .ColumnsNoBrackets([EventsTable.HOST_USERNAME]) \
                    .Like() \
                    .ValuesNoBrackets([host_id]) \
                    .And() \
                    .ColumnsNoBrackets([EventsTable.ID]) \
                    .Like() \
                    .ValuesNoBrackets([event_id]) \
                    .Build()

                cursor.execute(sql)
                columns = cursor.description

                events = []

                for values in cursor.fetchall():
                    events.append(DAOHelper.ConvertResultsToObject(columns, values)[0])

                return events
        except:
            return None

        finally:
            cursor.close()
            self.db.close()

