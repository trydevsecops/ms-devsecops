pipeline {
    agent any

    environment{
        Repo_URL="https://github.com/trydevsecops/ms-devsecops.git"
    }

 stages {
    stage('Checkout'){
        steps {
          checkout([$class: 'GitSCM', branches: [[name: "main"]], extensions: [], userRemoteConfigs: [[credentialsId: '62229006-efd2-4683-8be2-dfb195cd41c2', url: "$env.Repo_URL"]]])
        }
    }
    stage('Build and Test'){
        steps {
             sh "chmod +x ./gradlew && ./gradlew sonarqube "
        }
     }
    stage('sonarQube Analysis'){
	        steps {
	           withSonarQubeEnv('sonarqube'){
                   sh "./gradlew sonarqube "
	           }
            }

    }
    stage("Quality Gate"){
        steps {
           waitForQualityGate abortPipeline: true
        }
    }
    stage('Docker Build'){
	  steps {
             sh "docker build . -t ms-devsecapps:latest"
       }
    }



}

}
