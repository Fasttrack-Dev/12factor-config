# 12factor config

This is a small example how to provide environment-specific configuration to your
application via environment properties. Simply run the pipeline
```shell
./pipeline
```
It will
- create (update if already present) a Beanstalk dev and prod environment
- create (update if already present) a Memcached cluster for development with one node
- create (update if already present) a Memcached cluster for production with two nodes
- deploy a sample application (see below)
- configure the deployed applications according to their environment  
- configure the necessary security groups

As the setup might take some time to finish, it is recommended to refresh the
security token before executing the script, so that the token does not expire
in-flight.

## view the environment variables
At frist you need a overview of your environments. In case you have more then one, this command shows only the `12factor-config` related envs.
```sh
$ aws elasticbeanstalk describe-environments | jq '.Environments[] | select(.EnvironmentName | startswith("12factor-config"))'
```
Now you can get the ENV variables with help of this command. <br>
_hint_ the values for `--application-name` and `--environment-name` are provided by command just now.
```sh
aws elasticbeanstalk describe-configuration-settings --application-name 12factor-config --environment-name 12factor-config-prod | jq '.ConfigurationSettings[] | .OptionSettings[] | select(.OptionName | startswith("EnvironmentVariables"))'

{
  "Namespace": "aws:cloudformation:template:parameter",
  "OptionName": "EnvironmentVariables",
  "Value": "SERVER_PORT=5000,MEMCACHED_SERVER=twelve-factor-config-prod-cluster.8qkbg2.cfg.euw1.cache.amazonaws.com:11211,M2=/usr/local/apache-maven/bin,M2_HOME=/usr/local/apache-maven,JAVA_HOME=/usr/lib/jvm/java,GRADLE_HOME=/usr/local/gradle"
}
```

## Sample Application
The sample application has an endpoint to set a value into the cache and one
to retrieve cached values:
```
PUT http://<environment>/set?key=<key>&value=<value>
```
```
GET http://<environment>/get?key=<key>
```
examples for both actions:
```sh
$ curl -X PUT 'http://12factor-config-prod.xxx.region.elasticbeanstalk.com/set?key=huhu&value=ahhh'
```
```sh
$ curl -X GET 'http://12factor-config-prod.xxx.region.elasticbeanstalk.com/get?key=huhu'
```

To remove the example, call the `teardown` script. It will remove the manual security
group configurations and then delete the CloudFormation stack.
