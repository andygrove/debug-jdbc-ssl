
import java.io.File
import java.sql.{Connection, DriverManager}

import org.rogach.scallop.{ScallopConf, ScallopOption}

/**
  * Quick and dirty utility for testing JDBC SSL connection issues
  */
object DebugSSL {

  def main(arg: Array[String]): Unit = {

    val conf = new Config(arg)

    val tsLocation = conf.truststorePath.apply()
    if (new File(tsLocation).exists) {
      System.setProperty("javax.net.ssl.trustStore", tsLocation)
    } else {
      throw new RuntimeException(s"Cannot find truststore at $tsLocation ... pwd = ${new File(".").getAbsolutePath}")
    }

    Class.forName("com.mysql.jdbc.Driver")
    
    System.setProperty("javax.net.debug", "ssl")

    val connectionString = s"jdbc:mysql://${conf.mysqlHost.apply()}:${conf.mysqlPort.apply()}?useSSL=true&verifyServerCertificate=true"

    println(s"Connection string: $connectionString")
    val conn: Connection = DriverManager.getConnection(connectionString, conf.mysqlUser.apply(), conf.mysqlPass.apply())

    println("Connected OK")
    conn.close()

  }

}

class Config(args: Seq[String]) extends ScallopConf(args) {
  val truststorePath: ScallopOption[String] = opt[String]("truststore", 't', "A path to a truststore", required = true)
  val mysqlHost: ScallopOption[String] = opt[String]("host", 'h', "Host", required = true)
  val mysqlPort: ScallopOption[Int] = opt[Int]("port", 'P', "Port", required = true)
  val mysqlUser: ScallopOption[String] = opt[String]("user", 'u', "User", required = true)
  val mysqlPass: ScallopOption[String] = opt[String]("password", 'p', "Password", required = true)
  verify()
}

