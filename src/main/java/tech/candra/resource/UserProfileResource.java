package tech.candra.resource;


import io.quarkus.logging.Log;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import tech.candra.dto.UserProfileRequest;
import tech.candra.service.UserAdminService;
import tech.candra.service.UserProfileService;
import tech.candra.templates.Template;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Slf4j
public class UserProfileResource {
    @Inject
    UserProfileService userProfileService;
    @Inject
    JsonWebToken jwt;

    //end-point untuk create userProfile
    @POST
    @Path("/createUserProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "add User Profile", description = "silakan masukkan profile user sesuai format")
    public Template addUserProfile(UserProfileRequest userProfileRequest) {

        //claim id dari token
        String idAdminUser = jwt.getClaim("id").toString();
        Log.info("id admin user = " + idAdminUser);

        Template response = userProfileService.post(userProfileRequest, idAdminUser);
        return response;

    }

    //end-point untuk update user profile
    @PUT
    @Path("/updateUserProfile")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    @Operation(summary = "Update Profile User", description = "Silahkan masukan profile yang akan diperbaharui")
    public Template updateUserProfile(UserProfileRequest userProfileRequest) {

        //claim id dari token
        String idAdminUser = jwt.getClaim("id").toString();
        Log.info("subject = " + idAdminUser);

        Template response = userProfileService.updateUserProfile(userProfileRequest, idAdminUser);
        return response;
    }

    //end-point untuk get user profile
    @GET
    @Path("/getUserProfile")
    @RolesAllowed("admin")
    @Operation(summary = "Mendapatkan User Profile ", description = "gunakan token login")
    public Template getUserProfilebyId() {

        String idAdminUser = jwt.getClaim("id").toString();
        System.out.println("subject = " + idAdminUser);
        Template response = userProfileService.getUserProfile(idAdminUser);
        return response;
    }


    @DELETE
    @Path("/deleteUserProfile")
    @RolesAllowed("admin")
    //@SecurityRequirement(name = "Bearer JWT Token")
    @Operation(summary = "Hapus user profile ", description = "gunakan token")
    public Template deleteUserProfile() {
        String idAdminUser = jwt.getClaim("id").toString();
        System.out.println("subject = " + idAdminUser);

        Template response = userProfileService.deleteUserProfile(idAdminUser);
        return response;
    }

}
