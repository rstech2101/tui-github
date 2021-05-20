
# Overview
This project is used to fetch the public GitHub repository information of a user. It will fetch the repository name, owner login, and branch information.

# Prerequisite
To run the application on a local system, the System should require <b>Java 8</b>

## Step 1: Download the code from GitHub
```
git clone https://github.com/rstech2101/tui-github.git
```

## Step 2: Build the project

On Linux or Ubuntu, we will use the below command

```
./gradlew build
```

On windows, we will use the below command

```
gradlew.bat build
```

## Step 3: To Start the application
```
./gradlew bootRun
```

## Step 4: To Test the API using SwaggerUI on local environment
User can use it to test for 200 and 404 http status.

```
http://localhost:8080/swagger-ui.html
```

The RestApi application endpoint is below:

```
http://localhost:8080/git/api/v1/user/repos/{user_name}
```

To test the endpoint, the user needs to replace the <b>{user_name}</b> with the userName for which the user wants to test.

## Step 5: To test the API using CURL or any HTTP API Tool like Postman/RestClient

<b>Case 1:</b> When given userName is not an existing github user, we will get <b>404</b> as response

```
curl -X GET "http://localhost:8080/git/api/v1/user/repos/rstech21012111" -H  "accept: application/json"
```
The sample response for the above curl command is:

```
{
  "status": 404,
  "message": "rstech21012111 is not an existing Github User. Please provide valid user_name."
}
```

<b>Case 2:</b> When given userName is an existing GitHub user and we will fetch all his GitHub repositories, which are not forks. We will get <b>200</b> as response:

```
curl -X GET "http://localhost:8080/git/api/v1/user/repos/rstech2101" -H  "accept: application/json"
```

The sample response for the above curl command is:

```
[
  {
    "repository_name": "jenkins-deployment",
    "owner_name": "rstech2101",
    "branch_information": [
      {
        "branch_name": "master",
        "last_commit_sha": "5b98556a3e8725f39ca44747c0178c8922e63b60"
      }
    ]
  },
  {
    "repository_name": "tui-github",
    "owner_name": "rstech2101",
    "branch_information": [
      {
        "branch_name": "master",
        "last_commit_sha": "7d0783d476246358ef6b93f6a6af4f0ca22ef6d2"
      },
      {
        "branch_name": "rstech2101-patch-1",
        "last_commit_sha": "affd0e8cc9be27bad253fd58b6dd15c1793fe2b6"
      }
    ]
  }
]
```

<b>Case3:</b> When we provide header as Accept: application/xml, we will get <b>406</b> response

```
curl -X GET "http://localhost:8080/git/api/v1/user/repos/rstech2101" -H "accept: application/xml"
```

The sample response for the above curl command is:

```
{
  "status": 406,
  "message": "Method accept application/json in header. Please provide the valid header."
}
```

# Application Deployment on AWS using Jenkins from Local System/Server
To deploy the application on AWS, we need to first create the docker image then push that docker image to docker hub. 
After that we will fetch the image from docker hub and then deploy it to AWS.

To create the resources in AWS, we will be using cloudformation script. CloudFormation template will be deployed by Jenkins into AWS.
Jenkins will use to orchestrated them. 
AWS will provision all the resources that we will describe in our template, including:
1. <b>ECS cluster</b> - ECS tasks will be deploy there.
2. <b>ECS task definition</b> - A description, how Docker image should run, including image, name and tag.
3. <b>ECS service</b> - creates and manages ECS tasks, which represent a running Docker container
4. <b>Security group</b> - It defines which ports and IP addresses allowed to inbound and outbound traffic from/to 


# Deployment Prerequisite
To deploy the application on AWS, user should have:
1. Acccount on AWS
2. Account on DockerHub

Apart from user configuration, system should have:
1. Java8
2. docker (<a href="https://docs.docker.com/desktop/">https://docs.docker.com/desktop/</a> reference link to install docker on system)
3. docker daemon should be up and running 

# Deployment Process :  
->  User need to download/clone one more repository. The repository url is : <a href="https://github.com/rstech2101/jenkins-deployment.git">https://github.com/rstech2101/jenkins-deployment.git</a> . New repository is part of bootstrapping strategy.

->  In jenkins-deployment project, If we are login as root user then will use below command: 

```
./gradlew docker dockerRun
```

If we are logging and running the command with other than root user. we will need to use below command:

```
sudo ./gradlew docker dockerRun
```

-> Check Jenkins is up and running, we will get the below message on terminal. For reference image is available in <b>documents</b> folder as <b>JenkinsStart.png</b>

-> After that on browser open the Jenkins using <a href="http:\\localhost:8080">http:\\localhost:8080</a>. For reference image is available in <b>documents</b> folder as <b>localhost8080.png</b>

-> Once we open the Jenkins, then user need to add the credentails for <b>aws</b> and <b>docker-hub</b>. For aws-credentials, we will use AccessKey and SecretKey. For docker-hub, we will use userName and password.

-> After that, user need to add the Environment variables <b>REGION</b> and <b>SUBNET_ID</b>. 

-> After that user will run the <b>seed-job</b>, seed-job will create new job <b>tui-github-job-aws</b>

-> At the end, user will run the <b>tui-github-job-aws</b> job to deploy the build on AWS, first time it will take around 5 minutes to deploy on AWS, Once it download all the dependencies and create the stack on AWS then from next time onwards it will take very less time for deployment.

-> If user update/merge in master branch then <b>tui-github-job-aws</b> job will automatically trigger and deploy the build on AWS.

-> For reference pdf is available in <b>documents</b> folder as <b>JenkinsToAwsDeploymentAndTest.pdf</b>. User can check and follow the steps.

```diff
I did the deployment testing on ubuntu 18.04. I don't have mac or windows system to test it.
```