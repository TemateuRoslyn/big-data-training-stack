from jobs.mysql.mysql_db_init import MysqlDBInit

class DBInit:
    def __init__(self, spark):
        self.spark = spark

    def run(self):
        MysqlDBInit(self.spark, None).init_orders_csv()
