# Test project for GitHub Gist API

Gist token is generated for test GitHub user `alex-chumakin-test`.  
Password is storing in config-file in encrypted mode to show that sensitive data should not be stored in raw view.

## Prerequisites
- JDK11 - can be installed from this link: 
https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html  
> don't forget to add root Java folder installation path to $JAVA_HOME environment variable
- Maven - can be installed from this link:
http://maven.apache.org/download.cgi

## To run tests
`mvn test`

#### There is different types of verifications are listed:
 - Built-in assertion of REST Assured using hamcrest matchers (`AccessibilityTest`)
 - JUnit 5 assertion (`GetGistTest`)
 - More advanced assertion with assertj library (`CreateGistTest`) - might be used together with JUnit assertions
  in case when need to compare objects ignoring nested classes fields

> note:
*do not upgrade lombok version to 1.18.8 due to incompatible behavior with newest jackson library*
