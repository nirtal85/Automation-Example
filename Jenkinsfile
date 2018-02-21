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
    checkout([$class: 'GitSCM',
     branches: [
      [name: 'master']
     ],
     doGenerateSubmoduleConfigurations: false,
     userRemoteConfigs: [
      [credentialsId: '3ba8dc10-95a7-4f0d-85fd-3c5a9ab0a15b',
       url: 'https://github.com/nirtal85/AutomationExample.git'
      ]
     ]
    ])
   }
  }
  stage('Run Tests') {
   steps {
    bat '''mvn clean test'''
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
   mail bcc: '', body: 'Please review the build on "dasdasdsa"', cc: '', from: 'nirt236@gmail.com', replyTo: '', subject: 'Jenkins build Status', to: 'nirt236@gmail.com'
  }
 }
}
