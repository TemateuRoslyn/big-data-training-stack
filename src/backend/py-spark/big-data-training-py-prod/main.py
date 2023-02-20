import os
from pyspark.sql import SparkSession
from db_init import DBInit

class Main:

    def __init__(self):
        print("\n========================================= PY-SPARK APP IS READY ====================================================\n")
        self.spark = SparkSession \
                     .builder \
                     .config("spark.driver.extraClassPath", os.environ['MYSQL_CONNECTOR_JAR']) \
                     .config("spark.driver.extraClassPath", os.environ['POSTGRESQL_CONNECTOR_JAR']) \
                     .appName('big-data-training-py-prod') \
                     .getOrCreate()

    def run(self):
        DBInit(self.spark).run()

# entry point for PySpark ETL application
if __name__ == '__main__':
    Main().run()
    print("\n========================================= PY-SPARK APP IS KO ====================================================\n")
