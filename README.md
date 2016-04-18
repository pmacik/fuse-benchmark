# JBoss Fuse Benchmark

## Prepare Fuse
* Get and unzip Fuse archive (let's call the Fuse's directory `$FUSE`). Out-of-the-box it is prepared for the Fuse version `6.2.1.redhat-084`.
* Enable *admin* user (with password *admin*) by uncommenting following line in `$FUSE\etc\users.properties` file.
```
admin=admin,...
```
* Bind ActiveMQ network interfaces to to `0.0.0.0` by setting `activemq.host` property in `$FUSE\etc\system.properties` file.
* Enable MQTT connector of ActiveMQ by adding following transport connector in `$FUSE\etc\activemq.xml` file:
```xml
<beans>
    ...
    <broker>
        ...
        <transportConnectors>
            ...
            <transportConnector name="mqtt" uri="mqtt://${bindAddress}:1883"/>
        </transportConnectors>
     </broker>
</beans>
```
* Install `camel-mqtt` feature by default by adding it to `featuresBoot` property in `$FUSE\etc\org.apache.karaf.features.cfg` file:
```
...
featuresBoot=\
        ...,\
        camel-mqtt
...

```

## Prepare benchmark scenarios
A benchmark test consists of 2 parts:
* Tested bundle (server side)
* [PerfCake](http://www.perfcake.org) scenario (client side)

### Server side

#### Build tested bundle
All tested bundles are Maven projects that can be build by the following command:
```sh
mvn clean package
```
After a successful build there is a jar file (the bundle) prepared in `target` directory.

> Note: Currently and out-of-the-box the benchmark is set for the version `6.2.1.redhat-084` of Fuse. The version numbers can be overriden by setting the following Jave system properties for the maven build:
>```
> -Dfuse.major.version=6
> -Dfuse.minor.version=2
> -Dfuse.micro.version=1
> -Dfuse.build.version=084
>```
> The bundles uses Apache ActiveMQ and CXF artifacts whose versions are set to the particular versions that are part of Fuse as well.
> To override those versions, use the following Java system properties during the Maven build:
>```
>-Dversion.activemq=...
>-Dversion.cxf=...
>```

#### Deploy tested bundle into Fuse
To deploy the tested bundle simply place the bundle jar file built in the previous step into `$FUSE\deploy` directory.

### Client side - Prepare PerfCake
* [Get](https://perfcake.ci.cloudbees.com/job/PerfCake-devel/lastBuild/artifact/perfcake/target/perfcake-7.0-SNAPSHOT-bin.zip)
   > Note: PerfCake (Currently version 7.0-SNAPSHOT is necessary to run the benchmark)

* Unpack the archive into a directory of your choice and let's call it `$PERFCAKE`.

* Place PerfCake scenarios (all the files from `fuse-benchmark\perfcake\scenarios` directory) into `$PERFCAKE\resources\scenarios` directory

* Place scenarios' messages (all the files from `fuse-benchmark\perfcake\messages` directory) into `$PERFCAKE\resources\messages` directory

## Run the benchmark scenario

### Server side
The Fuse should be running with the particular bundle deployed and started

### Client side
* Execute the PerfCake scenario by running the following command:
```sh
$PERFCAKE\bin\perfcake.sh -s <scenario-name> -Dserver.host=<fuse-hostname>
```
where: `<scenario-name>` is the name of the scenario file without the `.xml` extension and `<fuse-hostname>` is the network address (hostname or IP address) of the machine where Fuse is running.
* Wait for PerfCake to to finish. The scenarios are configured to run with 100 concurrent threads for 5 minutes (plus a warm-up period) using 1kB message. You'll see the progress and partial results in log output for the whole duration of scenario execution.

>Note: The duration, number of threads or message size can be tweaked by the following Java system properties set during the PerfCake run:
>* `perfcake.run.type` - the type of the duration, one of `time` or `iteration`
>* `perfcake.run.duration` - amount of time (in milliseconds) in case of `time` run type, or the number of iterations in case of `iteration` run type
>* `perfcake.thread.count` - the number of concurrent threads (number of clients)
>* `perfcake.message.size` - the message size, one of `1B`, `16B`, `1kB` or `5kB`
>
>The previous (default) command is equivalent to:
>```
> $PERFCAKE\bin\perfcake.sh -s <scenario-name> -Dserver.host=<fuse-hostname> -Dperfcake.run.type=time -Dperfcake.run.duration=300000 -Dperfcake.thread.count=100 -Dperfcake.message.size=1kB
>```

## Collect the results
After a successful run of the PerfCake you will find the results in 2 forms:
* CSV files `$PERFCAKE\<scenario-name>-<message-size>-throughput-stats-<timestamp>.csv` and `$PERFCAKE\<scenario-name>-<message-size>-response-time-stats-<timestamp>.csv` with the history of the benchmark run, where the last record is the final result.
* A HTML report with nice charts found in `$PERFCAKE\<scenario-name>-charts\index.html` directory.

Both reports show the same data.
