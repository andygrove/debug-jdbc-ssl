scalaVersion := "2.12.1"

libraryDependencies += "org.rogach" %% "scallop" % "2.1.3"
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.6"

mainClass in Compile := Some("DebugSSL")