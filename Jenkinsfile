pipeline {
  agent any
  stages {
    stage('Checkout Scm') {
      steps {
        git(credentialsId: 'github/jciarka', url: 'https://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@github.com/jciarka/PIS-2022L-KAFKA-PROD.git', branch: 'development')
      }
    }

    stage('Deploy') {
      steps {
        sh '''#  Instal new version on current machine
docker-compose -f kafka-docker-compose.yaml down
docker-compose -f mongo-docker-compose.yaml down
docker-compose -f kafka-docker-compose.yaml up -d
docker-compose -f mongo-docker-compose.yaml up -d'''
      }
    }
  }
  triggers {
    pollSCM('* * * * *')
  }
}