package tech.candra.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import tech.candra.dto.ConfirmationToken;
import tech.candra.dto.UserAdminRequest;
import tech.candra.models.UserActivation;
import tech.candra.service.UserAdminService;
import tech.candra.templates.Template;
import tech.candra.templates.ValidasiTemplate;
import tech.candra.util.RegexUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/api")
public class UserAdminResource {
    @Inject
    UserAdminService userAdminService;

    //end-point untuk create user admin
    @POST
    @Path("/createUserAdmin")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add User Admin", description = "Silahkan masukan data user sesuai format")
    public Template addUserAdmin( UserAdminRequest userAdminRequest) {

        //validasi userAdminRequest
        Template responseValidasi = ValidasiTemplate.validasiUserAdminRequest(userAdminRequest);
        if (responseValidasi != null) {
            return responseValidasi;
        }
        String password = RegexUtil.generatePassword();
        userAdminRequest.setPassword(password);
        UserActivation userActivation = new UserActivation();
        userActivation.setEmail(userAdminRequest.getEmail());
        //masuk ke user admin service
        Template response = userAdminService.post(userAdminRequest);
        return response;
    }
    @GET
    @Path("/confirmation-token")
    @Operation(summary = "confirm token", description = "please put your token")
    public Object confirmationToken(@QueryParam("token") String token,@QueryParam("email") String email ){
        return userAdminService.confirmationToken(token, email);
    }
    @POST
    @Path("/req-forgot-password/{email}")
    @Operation(summary = "forgot password", description = "please put your token")
    public Object reqForgot(@PathParam("email") String email){
        return userAdminService.forgotRequest(email);
    }
    @GET
    @Path("/confirm-password")
    @Operation(summary = "confirm token", description = "please put your token")
    public Object confirmForgot(@QueryParam("token") String token,@QueryParam("email") String email ){
        return userAdminService.confirmForgotPassword(token, email);
    }
    @POST
    @Path("/forgot-password")
    @Operation(summary = "forgot password", description = "please put your token")
    public Object forgotPassword(ConfirmationToken confirmationToken){
        String password = RegexUtil.generatePassword();
        confirmationToken.setPassword(password);
        return userAdminService.changePassword(confirmationToken);
    }

    //end-point untuk update user admin
    @PUT
    @Path("/updateUserAdmin/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update data User", description = "Silahkan masukan data yang akan diperbaharui")
    public Template updateUserAdmin(UserAdminRequest userAdminRequest, @PathParam("id") Long id) {
        //validasi userAdminRequest
        Template responseValidasi = ValidasiTemplate.validasiUserAdminRequest(userAdminRequest);
        if (responseValidasi != null) {
            return responseValidasi;
        }
        //masuk ke user admin service method edit
        Template response = userAdminService.editUserAdmin(userAdminRequest, id);
        return response;

    }

    //end-point untuk menghapus user admin
    @DELETE
    @Path("/deleteUserAdmin/{id}")
    @Operation(summary = "Hapus User Admin berdasarkan id", description = "Silahkan masukan id")
    public Template deleteUserAdmin(@PathParam("id") Long id) {

        //masuk ke user admin service method delete
        Template response = userAdminService.deleteUser(id);
        return response;
    }

    //end-point untuk membaca data berdasarkan id
    @GET
    @Path("/getUserAdmin/{id}")
    @Operation(summary = "Mendapatkan User Admin berdasarkan id", description = "Silahkan masukkan id")
    public Template getUserAdmin(@PathParam("id") Long id) {

        //masuk ke user admin service method getuser
        Template response = userAdminService.getUser(id);
        return response;
    }

    //end-point untuk membaca data secara keseluruhan
    @GET
    @Path("/getUserAdmin/All")
    @Operation(summary = "Menampilkan User Admin secara Keseluruhan", description = " ")
    public Template getUserAdminAll() {

        //masuk ke user admin service method getuser
        Template userAll = userAdminService.getUserAll();
        return userAll;
    }



}
