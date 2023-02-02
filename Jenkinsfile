pipeline {
    agent any

    environment{
        Repo_URL="https://github.com/trydevsecops/ms-devsecops.git"
        CONTAINER_REGISTRY_URL="https://jenkings.eastus.cloudapp.azure.com/devsecops"
        CONTAINER_REGISTRY="jenkings.eastus.cloudapp.azure.com/devsecops"
        /*DOCKERHUB_CREDENTIALS= credentials('dockerhubcredentials')*/
        DOCKERHUB_CREDENTIALS= credentials('harborcredentials')
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
             sh "chmod +x ./gradlew && ./gradlew clean build "
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
             sh "docker build . -t $env.CONTAINER_REGISTRY/$env.IMAGE_NAME:$env.VERSION"
       }
    }
    stage('Scan Docker Image'){
      steps{
          sh "trivy image --format template --template @./trivy-jenkin.tpl  $env.CONTAINER_REGISTRY/$env.IMAGE_NAME:$env.VERSION"
      }
    }
    /*stage('Validate Scanner Output') {
          steps{
               script
               {
                  if(manager.logContains('.*ScanResult:Failed.*')) {
                    sh "echo Docker Image Scanning : Failed"
                    error("Build Got Failed due to Docker Image Scanning Result..")
                  }
                  else{
                    sh "echo Docker Image Scanning : Success"
                  }
               }
          }
    }*/
    stage('Login to Container Registry') {
          steps{
    	        sh "docker login $env.CONTAINER_REGISTRY_URL --username $DOCKERHUB_CREDENTIALS_USR --password $DOCKERHUB_CREDENTIALS_PSW"
    	        echo 'Login Completed'
          }
    }
    stage('Push Image to Container Registry') {
          steps{
                /*sh "docker tag  $env.IMAGE_NAME:$env.VERSION   $env.CONTAINER_REGISTRY/$env.IMAGE_NAME:$env.VERSION"
                sh "docker tag ms-devsecapps:latest jenkings.eastus.cloudapp.azure.com/devsecops/ms-devsecapps:latest"
                sh "docker push jenkings.eastus.cloudapp.azure.com/devsecops/ms-devsecapps:latest " */

                sh "docker push $env.CONTAINER_REGISTRY/$env.IMAGE_NAME:$env.VERSION"
    	        echo 'Image Pushed'
          }
    }



  }
  post{
      always  {  sh 'docker logout' }
  }

}
