package tech.candra.service;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.panache.common.Sort;
import lombok.SneakyThrows;
import tech.candra.dto.ConfirmationToken;
import tech.candra.dto.UserAdminRequest;
import tech.candra.models.UserActivation;
import tech.candra.models.UserAdmin;
import tech.candra.templates.Template;
import tech.candra.util.RegexUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@ApplicationScoped
public class UserAdminService {
    @Inject
    Mailer mailer;

    @SneakyThrows
    @Transactional
    public Template post(UserAdminRequest userAdminRequest) {

        UserActivation userActivation = new UserActivation();

        System.out.println("Service");

        //mencari data berdasarkan username
        UserAdmin byUserAdmin = UserAdmin.findbyUsername(userAdminRequest.getUsername());
        //validasi username untuk username yang sudah digunakan
        if (byUserAdmin != null) {
            return new tech.candra.templates.Template(false, "username already exist", null);
        }
        UserAdmin em = UserAdmin.findByEmail(userAdminRequest.getEmail());
        //validasi username untuk username yang sudah digunakan
        if (em != null) {
            return new tech.candra.templates.Template(false, "email already exist", null);
        }

        //memanggil model useradmin
        UserAdmin userAdmin = new UserAdmin();
        //set data baru untuk useradmin
        userAdmin.setUsername(userAdminRequest.getUsername());

        userAdmin.setPassword(userAdminRequest.getPassword());
        userAdmin.setEmail(userAdminRequest.getEmail());
        userAdmin.setNamaLengkap(userAdminRequest.getNamaLengkap());
        userAdmin.setCreatedAt(LocalDateTime.now());
//        userAdmin.setIsActive(userAdminRequest.getIsActive());
        String token = RegexUtil.generatePassword();
        userActivation.setEmail(userAdminRequest.getEmail());
        userActivation.setToken(token);
        userActivation.setCreatedAt(LocalDateTime.now());
        String link = "http://localhost:8080/api/confirmation-token?token=";
//        UserActivation us = UserActivation.confirmToken(userAdmin);
//        URL url = new URL("http",token, userAdmin.getEmail());
        Mail message = Mail.withText(userAdminRequest.getEmail(),
                "ACTIVATE YOUR ACCOUNT", "Hello "+ userAdminRequest.getNamaLengkap()+
                        "\n this link to activate or verify your account \n" +
                        "http://localhost:8080/api/confirmation-token?token="+
                        userActivation.getToken()+"&email="+userAdminRequest.getEmail());
        mailer.send(message);
//        String verificationLink = generateVerificationLink(user.getId());
//        sendVerificationEmail(user.getEmail(), verificationLink);
        System.out.println("End Setting User");
        //save data user admin
        UserActivation.persist(userActivation);
        UserAdmin.persist(userAdmin);
        System.out.println("save user");

        //return template response
        return new tech.candra.templates.Template(
                true, "registration success please check your email", userAdmin.getUsername());

    }
    @Transactional
    public  Template confirmationToken(String token, String email){
        UserActivation userActivation = UserActivation.findByToken(token);
        if(userActivation == null){
            return  new tech.candra.templates.Template(false, "your token is not valid", null);
        }
        UserAdmin userAdmin = UserAdmin.findByEmail(email);
        if(userAdmin==null){
            return  new tech.candra.templates.Template(false,"your email is not match",null);
        }
        userAdmin.setIsActive(true);
        userActivation.deleteAll();
//        UserActivation.persist(userActivation);
        UserAdmin.persist(userAdmin);
        return new tech.candra.templates.Template(true,"confirmed", userAdmin);
    }

    @Transactional
    public Template forgotRequest(String email ) {

        UserActivation userActivation = new UserActivation();

        UserAdmin userAdmin = UserAdmin.findByEmail(email);
        //validasi username untuk username yang sudah digunakan
        if (userAdmin == null) {
            return new tech.candra.templates.Template(false, "email not exist", null);
        }
        String token = RegexUtil.generatePassword();
        userActivation.setEmail(email);
        userActivation.setToken(token);
        userActivation.setCreatedAt(LocalDateTime.now());
        Mail message = Mail.withText(email,
                "ACTIVATE YOUR ACCOUNT", "Hello "+ userAdmin.getUsername()+
                        "\n this link to reset password \n" +
                        "http://localhost:8080/api/confirm-password?token="+
                        userActivation.getToken()+"&email="+email);
        mailer.send(message);
        //save data user admin
        UserActivation.persist(userActivation);
        UserAdmin.persist(userAdmin);

        //return template response
        return new tech.candra.templates.Template(
                true, "cek your email to reset password ", userAdmin.getUsername());

    }
    @Transactional
    public  Template confirmForgotPassword(String token, String email){
        UserActivation userActivation = UserActivation.findByToken(token);
        if(userActivation == null){
            return  new tech.candra.templates.Template(false, "your token is not valid", null);
        }
        UserAdmin userAdmin = UserAdmin.findByEmail(email);
        if(userAdmin==null){
            return  new tech.candra.templates.Template(false,"your email is not match",null);
        }
        userAdmin.setToken("1");
        userActivation.deleteAll();
//        UserActivation.persist(userActivation);
        UserAdmin.persist(userAdmin);
        return new tech.candra.templates.Template(
                true,"confirmed\n reset your password", email);
    }
    @Transactional
    public Template changePassword(ConfirmationToken confirmationToken) {

        //mencari data berdasarkan id
        UserAdmin userAdmin = UserAdmin.findByEmail(confirmationToken.getEmail());
        userAdmin.getToken();
        if (userAdmin == null) {
            return new tech.candra.templates.Template(false, "user id tidak ditemukan", null);
        }
        if(userAdmin.getToken() == null){return new tech.candra.templates.Template(
                false, "check link reset pwd in your email", null);}
        userAdmin.setPassword(confirmationToken.getPassword());
        userAdmin.setUpdateAt(LocalDateTime.now());
        userAdmin.setToken(null);
        //save data (update)
        UserAdmin.persist(userAdmin);

        //return template succes
        return new tech.candra.templates.Template(true, "succes", userAdmin.getUsername());

    }
    @Transactional
    public Template editUserAdmin(UserAdminRequest userAdminRequest, long id) {
        System.out.println("Service");

        //mencari data berdasarkan id
        UserAdmin userAdminbyId = UserAdmin.findById(id);
        //validasi untuk id yang tidak valid
        if (userAdminbyId == null) {
            return new tech.candra.templates.Template(false, "user id tidak ditemukan", null);
        }

        //set data untuk useradmin (update)
        userAdminbyId.setUsername(userAdminRequest.getUsername());
        userAdminbyId.setPassword(userAdminRequest.getPassword());
        userAdminbyId.setEmail(userAdminRequest.getEmail());
        userAdminbyId.setNamaLengkap(userAdminRequest.getNamaLengkap());
        userAdminbyId.setUpdateAt(LocalDateTime.now());

        //save data (update)
        UserAdmin.persist(userAdminbyId);

        //return template succes
        return new tech.candra.templates.Template(true, "succes", userAdminbyId);

    }

    @Transactional
    public Template deleteUser(Long id) {
        //mencari data berdasarkan id
        PanacheEntityBase byId = UserAdmin.findById(id);

        //validasi data berdasarkan id yang akan dihapus
        if (byId == null) {
            //return untuk data yang id nya tidak ditemukan
            return new tech.candra.templates.Template(false, "id user tidak ditemukan", null);
        }

        //return succes data yang berhasil dihapus
        UserAdmin.deleteById(id);
        return new tech.candra.templates.Template(true, "succes", null);
    }

    public Template getUser(Long id) {

        //mencari data berdasarkan id
        UserAdmin byId = UserAdmin.findById(id);

        //validasi data berdasarkan id yang akan ditampilkan
        if (byId == null) {
            //return untuk data yang id nya tidak ditemukan
            return new tech.candra.templates.Template(false, "id user tidak ditemukan", null);
        }
        //membuat hashmap untuk mapping data tertentu (agar password tidak muncul di response)
        HashMap<Object, Object> map = new HashMap<>();
        map.put("id", byId.getId());
        map.put("fullname", byId.getNamaLengkap());
        map.put("username", byId.getUsername());
        map.put("email", byId.getEmail());

        //return template succes
        return new tech.candra.templates.Template(true, "succes", map);
    }

    public Template getUserAll() {

        //mencari data useradmin keseluruhan
        List<UserAdmin> userAdminList = UserAdmin.listAll(Sort.ascending("id"));

        //membuat variabel listuser
        ArrayList<Object> listUser = new ArrayList<>();
        //mapping data response menggunakan perulangan for
        for (UserAdmin userAdmin : userAdminList) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", userAdmin.getId());
            map.put("fullname", userAdmin.getNamaLengkap());
            map.put("username", userAdmin.getUsername());
            map.put("email", userAdmin.getEmail());

            //setting data map menjadi list baru
            listUser.add(map);

        }
        //return response succes
        return new tech.candra.templates.Template(true, "succes", listUser);
    }

}
