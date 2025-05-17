pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17'
            args '-u root'
        }
    }

    environment {
        EC2_IP = '44.201.90.0'                              // Your EC2 Public IP
        JAR_NAME = 'flightBooking-0.0.1-SNAPSHOT.jar'       // Your Spring Boot JAR name
        REMOTE_PATH = "/home/ec2-user/flightBooking-0.0.1-SNAPSHOT.jar"
    }

    stages {
        // Stage 1: Clone GitHub project
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Nitya-Joshi/Airline-Management-System.git'
            }
        }

        // Stage 2: Build the JAR
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        // Stage 3: Test phase
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        // Stage 4: Deploy to EC2
        stage('Deploy to EC2') {
            steps {
                withCredentials([file(credentialsId: 'ec2-pem-key', variable: 'PEM_PATH')]) {
                    sh '''#!/bin/bash
                        chmod 400 "$PEM_PATH"
                        scp -o StrictHostKeyChecking=no -i "$PEM_PATH" "target/${JAR_NAME}" ec2-user@${EC2_IP}:${REMOTE_PATH}
                    '''
                }
            }
        }

        // Stage 5: Start app on EC2
        stage('Start on EC2') {
            steps {
                withCredentials([file(credentialsId: 'ec2-pem-key', variable: 'PEM_PATH')]) {
                    sh '''#!/bin/bash
                        ssh -o StrictHostKeyChecking=no -i "$PEM_PATH" ec2-user@${EC2_IP} '
                            nohup /usr/bin/java -jar ${REMOTE_PATH} > app.log 2>&1 &
                        '
                    '''
                }
            }
        }
    }

    post {
        success {
            echo '✅ Application deployed successfully!'
            echo "URL: http://${EC2_IP}:8080"
        }
        failure {
            echo '❌ Deployment failed. Please check the logs.'
        }
    }
}
