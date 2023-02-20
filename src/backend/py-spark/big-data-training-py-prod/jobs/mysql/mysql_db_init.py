from .mysql_connector import MysqlConnector

class MysqlDBInit:

    def __init__(self, spark, db_con: MysqlConnector):
        self.db_con =  MysqlConnector()
        self.spark = spark
    
    def init_orders_csv(self):

        tablename = "2010_12_06_csv"



        df_reader = self.spark \
                        .read \
                        .format("csv") \
                        .option("header", "true") \
                        .load("tests/csv/2010-12-06.csv") 


        df_writer =  df_reader \
                        .write \
                        .mode("ignore") \
                        .format("jdbc") \
                        .option("driver", self.db_con.driver) \
                        .option("url", self.db_con.url) \
                        .option("dbtable", tablename) \
                        .option("user", self.db_con.username) \
                        .option("password", self.db_con.password) \
                        .save()








        


        
        