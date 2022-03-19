pipeline {
    agent any

    stages {
        stage('Install dependencies') {
            steps {
                sh "apt update && apt install docker-compose"
            }
        }

        stage('Build') {
            steps {
                echo 'Building..'
                sh "docker-compose -f docker-compose.yaml down"
                sh "docker-compose -f docker-compose.yaml up -d"
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}