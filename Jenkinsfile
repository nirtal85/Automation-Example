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
  timeout(time: 1, unit: 'HOURS')
 }
 parameters {
  string(name: 'email', defaultValue: 'nirt236@gmail.com', description: 'Send email on fauilre to')
 }
 stages {
  stage('SCM') {
   steps {
    git url: 'https://github.com/nirtal85/AutomationExample.git'
   }
  }
  stage("Quality Gate") {
   steps {
    withSonarQubeEnv('Sonar') {
     bat 'mvn sonar:sonar'
    }
    timeout(time: 1, unit: 'HOURS') {
     waitForQualityGate true
    }
   }
  }
  stage('Test') {
   steps {
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