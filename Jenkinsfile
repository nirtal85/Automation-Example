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
    slackSend(color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
    git url: 'https://github.com/nirtal85/AutomationExample.git'
    // jenkins task scanner - looks for FIXME and TODO tasks
    openTasks canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', high: 'FIXME', ignoreCase: true, low: '', normal: 'TODO', pattern: '**/*.java', unHealthy: ''
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
  success {
   slackSend(color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
  }
  failure {
   emailext(
    body: "Something is wrong with ${env.BUILD_URL}",
    subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
    to: "${params.email}"
   )
   slackSend(color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
  }
  cleanup {
   dir('allure-results') {
    deleteDir() /* clean up allure report for next run */
   }
  }
 }
}