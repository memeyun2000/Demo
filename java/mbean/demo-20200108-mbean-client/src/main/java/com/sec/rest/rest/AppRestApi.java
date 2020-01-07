package com.sec.rest.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class AppRestApi implements AppRestApiMBean {
    private static final Logger LOG = LoggerFactory.getLogger(AppRestApi.class);

    private String name;


    public AppRestApi() throws Exception{
    }

    public int getRestExecCount() {
        return 1989;
    }

    public String printHello() {
        return "hello world guoqingyun";
    }

    public String printHello(String message) {
        return "hello world " + message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the root endpoint Return always 200.
     *
     * @return 200 response
     */
    @GET
    public Response getRoot() {
        return Response.ok().build();
    }

    @GET
    @Path("info")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProjectInfo() {
        LOG.info("hello info");

        return javax.ws.rs.core.Response.status(Response.Status.OK).entity("{'SUCCESS':'TRUE'}").build();
    }


}
