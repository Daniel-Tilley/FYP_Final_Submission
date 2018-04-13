class QueryBuilder(object):
    def __init__(self):
        self.query = ""
        self.replaceableNulls = 0

    #########################################################################
    #                                                                       #
    # BASE SELECTORS                                                        #
    #                                                                       #
    #########################################################################

    def Select(self):
        self.query += "SELECT "
        return self

    def Insert(self):
        self.query += "INSERT "
        return self

    def Update(self):
        self.query += "UPDATE "
        return self

    def Delete(self):
        self.query += "DELETE "
        return self

    #########################################################################
    #                                                                       #
    # KEYWORDS                                                              #
    #                                                                       #
    #########################################################################

    def From(self):
        self.query += "FROM "
        return self

    def Into(self):
        self.query += "INTO "
        return self

    def Or(self):
        self.query += "OR "
        return self

    def Set(self):
        self.query += "SET "
        return self

    def Where(self):
        self.query += "WHERE "
        return self

    def Join(self):
        self.query += "JOIN "
        return self

    def On(self):
        self.query += "ON "
        return self

    def In(self):
        self.query += "IN "
        return self

    def Equals(self):
        self.query += "= "
        return self

    def And(self):
        self.query += "AND "
        return self

    def Between(self):
        self.query += "BETWEEN "
        return self

    def Like(self):
        self.query += "LIKE "
        return self

    def ValuesKeyword(self):
        self.query += "VALUES "
        return self

    def OrderBy(self):
        self.query += "ORDER BY "
        return self

    def Asc(self):
        self.query += "ASC "
        return self

    def Desc(self):
        self.query += "DESC "
        return self

    def Comma(self):
        self.query += ", "
        return self

    def AllColumns(self):
        self.query += "* "
        return self

    def LeftRoundedBracket(self):
        self.query += "( "
        return self

    def RightRoundedBracket(self):
        self.query += ") "
        return self

    #########################################################################
    #                                                                       #
    # PARAMETERS                                                            #
    #                                                                       #
    #########################################################################

    def Table(self, table_name):
        self.query += "`" + table_name + "` "
        return self

    def TableAllColumns(self, table_name):
        self.query += "`" + table_name + "`.* "
        return self

    def QueryValue(self, value):
        self.query += "'%" + value + "%' "
        return self

    def ColumnsNoBrackets(self, columns):

        for column in columns:
            self.query += "`" + column + "`, "

        self.query = self.query[:-2] + " "
        return self

    def ColumnsBrackets(self, columns):

        self.query += "("

        for column in columns:
            self.query += "`" + column + "`, "

        self.query = self.query[:-2] + ") "
        return self

    def TableColumnsNoBrackets(self, table_name, columns):

        for column in columns:
            self.query += "`" + table_name + "`." + "`" + column + "`, "

        self.query = self.query[:-2] + " "
        return self

    def TableColumnsBrackets(self, table_name, columns):

        self.query += "("

        for column in columns:
            self.query += "`" + table_name + "`." + "`" + column + "`, "

        self.query = self.query[:-2] + ") "
        return self

    def ValuesNoBrackets(self, values):

        for value in values:
            if value == 'None' or value == "None" or value is None or value == "%s":
                self.query += "%s" + ", "
            else:
                if isinstance(value, int):
                    self.query += str(value) + ", "
                elif isinstance(value, float):
                    self.query += str(value) + ", "
                else:
                    self.query += "'" + value + "', "

        self.query = self.query[:-2] + " "
        return self

    def ValuesBrackets(self, values):
        self.query += "("

        for value in values:

            if value == 'None' or value == "None" or value is None or value == "%s":
                self.query += "%s" + ", "
            else:
                if isinstance(value, int):
                    self.query += str(value) + ", "
                elif isinstance(value, float):
                    self.query += str(value) + ", "
                else:
                    self.query += "'" + value + "', "

        self.query = self.query[:-2] + ") "
        return self

    def UpdateValues(self, cols, values):

        for col, value in zip(cols, values):
            if value == 'None' or value == "None" or value == "%s" or value is None:
                self.query += "`" + col + "` = " + "%s" + ", "
            else:
                if isinstance(value, int):
                    self.query += "`" + col + "` = " + str(value) + ", "
                elif isinstance(value, float):
                    self.query += "`" + col + "` = " + str(value) + ", "
                else:
                    self.query += "`" + col + "` = " + "'" + value + "', "

        self.query = self.query[:-2] + " "
        return self

    def FunctionValues(self, function_name, args):
        self.query += "`" + function_name + "`("

        for arg in args:
            self.query += "'" + arg + "', "

        self.query = self.query[:-2] + ") "
        return self

    def FunctionColumns(self, function_name, args):
        self.query += "`" + function_name + "`("

        for arg in args:
            self.query += "`" + arg + "`, "

        self.query = self.query[:-2] + ") "
        return self

    def ExtendQuery(self, original_query):
        self.query += original_query
        return self

    #########################################################################
    #                                                                       #
    # VALUE SUBSTITUTION                                                    #
    #                                                                       #
    #########################################################################

    def CountNulls(self, sqlString):
        index = 0

        while index < len(sqlString):
            index = sqlString.find('%s', index)
            if index == -1:
                break
            index += 2
            self.replaceableNulls += 1

        return self

    def BuildNullTuple(self):
        null_tuple = list()

        if self.replaceableNulls == 0:
            return None

        else:
            for i in range(0, self.replaceableNulls):
                null_tuple.append(None)

            return tuple(null_tuple)

    #########################################################################
    #                                                                       #
    # RETURN QUERY                                                          #
    #                                                                       #
    #########################################################################

    def Build(self):
        return self.query
