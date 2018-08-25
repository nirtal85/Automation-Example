pipeline {
 agent {
  label {
   label ""
   customWorkspace "C:/Users/User/eclipse-workspace/AutomationExample"
  }
 }
 options {
  timestamps()
  buildDiscarder(logRotator(numToKeepStr: '3'))
 }
 stages {
  stage('SCM') {
   steps {
    git url: 'https://github.com/nirtal85/AutomationExample.git'
   }
  }
  stage('SonarQube analysis && Run Tests') {
   steps {
    withSonarQubeEnv('Sonar') {
     bat 'mvn sonar:sonar'
    }
    withMaven(jdk: 'Local JDK', maven: 'Local Maven') {
     bat 'mvn clean install test -DsuiteXmlFile=testng.xml'
    }
   }
  }
 }
 post {
  always {
   allure includeProperties: false, jdk: '', results: [
    [path: '/allure-results']
   ]
  }
  failure {
   emailext(
    body: "Something is wrong with ${env.BUILD_URL}",
    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
    to: "${params.email}"
   )
  }
 }
}