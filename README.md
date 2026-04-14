Setup steps:
First i have converted my simple java app to web spring boot app
Then i pushed it onto github 
Then i make a build image and pushed it to docker hub
Then I have already a pipeline in jenkins i just make a file and paste that pipeline in that jenkinsfile.
In that jenkins file we have stages and before that we set environment varibales such as repo name mac ip ssh config etc
Then in stages we have build test deploy etc stages
Then i make shared libararies to make our jenkins file just use those functions to call those groovy files e.g deploy.grpvy test.groovy etc
Then i configures the github webhook wo make our pipeline automated 
We have docker file to make image of our project upload it to docker hub and then pushed oour app to kubernetes 
Then i deployed my app through helm charts in which we have a folder structure to store diff functionality of data e.g chart.yaml file includes the basic information our app 
Then values.yaml file have  a our docckerhub user name with repo and a database config file
Then i used postgres-values.yaml for postgres persistance config and for database basic info like name pass etc
Then in that helm chart folder we have templates folder in which we have secret file config file deployment and values file (yaml)
In secret file we have we have database username and password to make it secret
deployment includes the deployment of postgres through helm charts with our app
then we have config map for database url db host port etc
also created a scema file for storing of data

APP FRONTEND
basic messaging app to store message on db and print that message on screen 

then i configure prometheus with our deployed app on kubenetes
installs the prometheus through helm repo which includes grafana also with monitoring tools
Ran the prometheus on localhost 9090 after port forwarding
Then create a servicemonitor app to access the end points metrics on prometheus
check the target of our app
Then extract grafana password and login on grafana 
select defualt prometheus datasource 
created a dashboard with simple queries
Then created a container of node exporter
add configuartion of our node exporter of our jenkins with jenkins ip and its port
Then again run the prometheus and chekc the target it shows the jenkins vm
Then again use grafan 
check the jenkins vm cpuuage memort usage and disk usage
created  a simple dashboard 
Finished
