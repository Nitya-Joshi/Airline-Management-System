pipeline {
    agent {
        docker {
            image 'maven:3.8.7-openjdk-17' // ✅ Java + Maven
        }
    }

    environment {
        // Replace with your real values or Jenkins credentials
        EC2_HOST = "ec2-user@<EC2_PUBLIC_IP>"
        JAR_NAME = "target/yourapp.jar" // adjust based on your project
        SSH_CREDENTIALS_ID = 'ec2-ssh-key'
    }

    stages {

        stage('Checkout Spring Boot Project') {
            steps {
                git 'https://github.com/yourusername/springboot-project.git'
            }
        }

        stage('Build the Project') {
            steps {
                sh 'mvn clean package -DskipTests=false'
            }
        }

        stage('Run Test Cases') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy JAR to EC2') {
            steps {
                sshagent (credentials: ["${SSH_CREDENTIALS_ID}"]) {
                    // Copy the jar file
                    sh "scp -o StrictHostKeyChecking=no ${JAR_NAME} ${EC2_HOST}:/home/ec2-user/"
                }
            }
        }

        stage('Start JAR on EC2') {
            steps {
                sshagent (credentials: ["${SSH_CREDENTIALS_ID}"]) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${EC2_HOST} '
                        # kill existing process if running
                        pkill -f "java -jar" || true
                        # start new process
                        nohup java -jar /home/ec2-user/${JAR_NAME.split('/')[-1]} > app.log 2>&1 &
                    '
                    """
                }
            }
        }
    }
}
