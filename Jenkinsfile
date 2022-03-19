pipeline {
    agent any

    stages {
        stage('Install dependencies') {
            steps {
                sh "apk update && 
                    apk add docker docker-compose
                    rc-update add docker default
                    /etc/init.d/docker start
                    "
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