pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh "export PACKAGE_VERSION=0.0.2"
                sh "docker tag pis-kafka-prod:$PACKAGE_VERSION jciarka/pis-kafka-prod:$PACKAGE_VERSION"
                sh "docker push jciarka/pis-kafka-prod:$PACKAGE_VERSION"
                sh "docker tag pis-kafka-prod:$PACKAGE_VERSION jciarka/pis-kafka-prod:latest"
                sh "docker push jciarka/pis-kafka-prod:latest"
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying....'
                sh "docker-compose -f docker-compose.yaml down"
                sh "docker-compose -f docker-compose.yaml up -d"
            }
        }
    }
}