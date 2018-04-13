class DAOHelper:

    def __init__(self):
        pass

    @staticmethod
    def ConvertResultsToObject(keys, values):
        items = []
        tmp = {}
        for (index, column) in enumerate(values):
            if column is not None:
                tmp[keys[index][0]] = str(column)
            else:
                tmp[keys[index][0]] = column
        items.append(tmp)

        return items

    @staticmethod
    def RemoveValueFromObject(key, item):
        item.pop(key)
        return item

    @staticmethod
    def ConvertArrayToTuple(array):

        items = ()

        for item in array:
            items += item

        return items
