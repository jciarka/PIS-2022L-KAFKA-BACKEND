pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                docker-compose -f docker-compose.yaml down
                docker-compose -f docker-compose.yaml up -d
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