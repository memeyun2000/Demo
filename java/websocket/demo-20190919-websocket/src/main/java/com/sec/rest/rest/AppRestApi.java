package com.sec.rest.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sec.rest.conf.MyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class AppRestApi {
    private static final Logger LOG = LoggerFactory.getLogger(AppRestApi.class);

    @Inject
    private MyConfiguration conf;

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

        conf.getConfig();

        return javax.ws.rs.core.Response.status(Response.Status.OK).entity("{'SUCCESS':'TRUE'}").build();
    }

    @GET
    @Path("conf")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getConfigInfo() {
        LOG.info("conf info");

        conf.getConfig();

        return javax.ws.rs.core.Response.status(Response.Status.OK).entity("{'SUCCESS':'TRUE'}").build();
    }

}
