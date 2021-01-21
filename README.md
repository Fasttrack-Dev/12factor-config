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

The sample application has an endpoint to set a value into the cache and one
to retrieve cached values:
```
PUT http://<environment>/set?key=<key>?value=<value>
```
```
GET http://<environment>/get?key=<key>
```

To remove the example, call the `teardown` script. It will remove the manual security
group configurations and then delete the CloudFormation stack.