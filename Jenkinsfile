node {
  stage('SCM') {
    //checkout scm
    checkout([$class: 'GitSCM', branches: [[name: "main"]], extensions: [], userRemoteConfigs: [[credentialsId: 'Github-Auth-Token', url: "https://github.com/trydevsecops/ms-devsecops.git"]]])
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      sh "./gradlew build sonar"
    }
  }
}

