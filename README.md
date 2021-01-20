# 12factor config

This is a small example how to provide environment-specific configuration to your
application via environment properties. Simply run the pipeline
```shell
./pipeline
```
It will
- create a Beanstalk dev and prod environment
- create a Memcached cluster for development with one node
- create a Memcached cluster for production with two nodes
- deploy a sample application (see below)
- configure the deployed applications according to their environment  
- setup the necessary security groups

The sample application has an endpoint to set a value into the cache and one
to retrieve cached values:
```
PUT http://<environment>/set?key=<key>?value=<value>
```
```
GET http://<environment>/get?key=<key>
```
