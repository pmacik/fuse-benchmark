Scenario camel-mqtt: MQTT exposed Java Bean
=

HTTP POST message is received from a client via Jetty HTTP published on the
`http://${server.host}:8888/fuse-performance-http-post-bean`
URL. The message is then passed to a Java bean where it is touched and returned back to the client via the Jetty
HTTP endpoint as a HTTP response.