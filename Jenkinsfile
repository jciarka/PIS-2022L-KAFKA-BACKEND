pipeline {
    agent any

    stages {
        stage('Install dependencies') {
            steps {
                sh "/usr/sbin/apk update && /usr/sbin/apk add docker docker-compose && /etc/init.d/docker start"
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