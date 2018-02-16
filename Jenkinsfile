pipeline {
    agent {
        label {
            label ""
            customWorkspace "C:/Users/User/eclipse-workspace/AutomationExample"
        }
    }
 stages {
  stage('Checkout') {
   steps {
         git(
       url: 'https://github.com/nirtal85/AutomationExample.git',
       credentialsId: '3ba8dc10-95a7-4f0d-85fd-3c5a9ab0a15b',
       branch: 'master'
       )
      bat '''git pull'''
    }
  }
  stage('run test') {
   steps {
    bat '''mvn clean test'''
   }
  }
 }
  post {
        always {
            allure includeProperties: false, jdk: '', results: [[path: '/allure-results']]
        }
        failure {
           mail bcc: '', body: 'Please review the build on "dasdasdsa"', cc: '', from: 'nirt236@gmail.com', replyTo: '', subject: 'Jenkins build Status', to: 'nirt236@gmail.com'
 
        }
    }
}
