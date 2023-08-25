package tech.candra.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.mutiny.ext.web.Session;
import org.eclipse.microprofile.openapi.annotations.Operation;
import tech.candra.dto.LoginRequest;
import tech.candra.service.LoginService;
import tech.candra.templates.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import javax.security.auth.Subject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/auth/login")
@ApplicationScoped
public class LoginResource {
    @Inject
    LoginService loginService;


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Login", description = "Silahkan masukan username dan password")
    public Template login( LoginRequest loginRequest) {
        Template response = loginService.login(loginRequest);

        return response;

    }
//    @POST
//    @Path("/login")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response login(@Valid @NotNull LoginRequest loginRequest) {
//        try {
//            // authenticate the user
//            Subject identity = identityController.authenticate(loginRequest.getUsername());
//
//            // create a new session
//            Session session = session.create();
//
//            // return the session ID as a response
//            return Response.ok(session.getDelegate()).build();
//        } catch (AuthenticationFailedException e) {
//            // return a 401 Unauthorized response
//            return Response.status(Response.Status.UNAUTHORIZED).build();
//        }
//    }

}
