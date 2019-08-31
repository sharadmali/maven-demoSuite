cd C:\EclipseWorkSpace\maven-demo
set classpath=C:\EclipseWorkSpace\maven-demo\bin;

mvn clean test -DsuiteXmlFile=/maven-demo/testng.xml

pause