package org.pmacik.fuse.benchmark;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class MessageBean {

   @GET
   @Path("/sayHello/{name}")
   @Consumes("text/plain")
   @Produces("text/plain")
   public String sayHelloGet(@PathParam("name") String name) {
      return sayHello(name);
   }

   @POST
   @Path("/sayHello")
   @Consumes("text/plain")
   @Produces("text/plain")
   public String sayHelloPost(String name) {
      return sayHello(name);
   }

   private String sayHello(String name) {
      return "Hello " + name;
   }
}
