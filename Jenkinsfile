pipeline {
    agent any

    environment{
        Repo_URL="https://github.com/trydevsecops/ms-devsecops.git"
    }

 stages {
    stage('Checkout'){
        steps {
          checkout([$class: 'GitSCM', branches: [[name: "main"]], extensions: [], userRemoteConfigs: [[credentialsId: '330737e3-cc71-4520-97dd-fde1f2c52e91', url: "$env.Repo_URL"]]])
        }
    }
    stage('Build and Test'){
        steps {
              sh "gradle clean build"
        }
     }
    stage('sonarQube Analysis'){
	  steps {
            withSonarQubeEnv('sonarqube') {
             sh "gradle sonarqube "
           }
       }
    }

}

}
