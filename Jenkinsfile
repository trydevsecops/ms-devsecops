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
          sh "trivy image --format template --template '{{- \$critical := 0 }}{{- \$high := 0 }}{{- range . }}{{- range .Vulnerabilities }}{{- if  eq .Severity "CRITICAL" }}{{- \$critical = add \$critical 1 }}{{- end }}{{- if  eq .Severity "HIGH" }}{{- \$high = add \$high 1 }}{{- end }}{{- end }}{{- end }}{{- if  ge \$critical 1 }}Critical:{{ \$critical }}, ScanResult:Failed {{- end }}{{- if  ge \$high 1 }} High:{{ \$high }}, ScanResult:Failed{{- end }}'  ms-devsecapps:latest  $env.CONTAINER_REGISTRY/$env.IMAGE_NAME:$env.VERSION -f json"
      }
    }
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
    /*stage('Scan Container Image') {
          steps{
    	        sh "docker scan $env.IMAGE_NAME:$env.VERSION --json "
    	        echo 'Docker Scan Completed'
          }
    }*/


  }
  post{
      always {
        sh 'docker logout'
      }
  }

}
