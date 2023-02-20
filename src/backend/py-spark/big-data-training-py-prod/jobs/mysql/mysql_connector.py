
class MysqlConnector:

    def __init__(self,
                username:str = "userBigdata",
                password:str = "6gUSN9N_/.mJ686", 
                driver: str  = "com.mysql.jdbc.Driver", 
                driverurl: str  = "jdbc:mysql://",
                host: str    = "192.168.43.31", 
                port: str    = "3308", 
                db_name: str = "bigdata_test_db",
                url: str     = "jdbc:mysql://192.168.43.31:3308/bigdata_test_db?autoReconnect=true&useSSL=false"):
        self.username  = username
        self.password  = password
        self.driver  = driver
        self.driverurl  = driverurl
        self.driver  = driver
        self.host    = host
        self.port    = port
        self.db_name = db_name
        self.url     = driverurl + host + ":" + port + "/" + db_name
    