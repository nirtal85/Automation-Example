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
      mail to: 'nirt236@gmail.com',
             subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
             body: "Something is wrong with ${env.BUILD_URL}"  
  }
 }
}
