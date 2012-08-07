/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package docservice.common;

import javax.activation.DataHandler;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/doc")
public interface DocService {

    @Path("/{id}")
    @GET
    @Produces("application/xml")
    public DataHandler read(@PathParam("id") String id);
    
    @Path("/{id}")
    @PUT
    @Consumes("application/xml")
    public void write(@PathParam("id") String id, DataHandler content);

}
