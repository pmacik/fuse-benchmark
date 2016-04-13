Scenario fuse-jaxrs-get: Plain JAX-RS endpoint (GET method)
=

HTTP GET message is received from a client via JAX-RS service published on the
`http://${server.host}:8181/cxf/fuse-performance-jaxrs/sayHello/${message}`
URL. The message is then touched by the JAX-RS service and returned back to the client as a HTTP response.

The message content is passed as a part of the URL - the `${message}` part.

Scenario fuse-jaxrs-post: Plain JAX-RS endpoint (POST method)
=

HTTP POST message is received from a client via JAX-RS service published on the
`http://${server.host}:8181/cxf/fuse-performance-jaxrs/sayHello`
URL. The message is then touched by the JAX-RS service and returned back to the client as a HTTP response.

The message content is passed as the HTTP request's body.