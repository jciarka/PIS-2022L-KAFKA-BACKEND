pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh "docker login -u $DOCKER_USER -p $DOCKER_PWD"
                sh "PACKAGE_VERSION=$(grep "version =" ./build.gradle | awk '{print $3}' | sed "s/'//" | sed "s/'//")"
                sh "docker build -t pis-kafka-prod:$PACKAGE_VERSION --build-arg VERSION=$PACKAGE_VERSION ."
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