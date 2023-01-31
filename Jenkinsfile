pipeline {
    agent any

    environment{
        Repo_URL="https://github.com/trydevsecops/ms-devsecops.git"
        DOCKERHUB_CREDENTIALS= credentials('dockerhubcredentials')
        IMAGE_NAME="ms-devsecapps"
        VERSION="latest"
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
             sh "docker build . -t $env.IMAGE_NAME:$env.VERSION"
       }
    }
    stage('Login to Container Registry') {
          steps{
    	        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | sudo docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
    	        echo 'Login Completed'
          }
    }
    stage('Scan Container Image') {
          steps{
    	        sh 'docker scan $env.IMAGE_NAME'
    	        echo 'Login Completed'
          }
    }


  }
  post{
      always {
        sh 'docker logout'
      }
  }

}
