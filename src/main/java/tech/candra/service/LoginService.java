package tech.candra.service;

import io.quarkus.mailer.Mailer;
import io.vertx.mutiny.ext.web.Session;
import tech.candra.dto.LoginRequest;
import tech.candra.models.UserAdmin;
import tech.candra.templates.Template;
import tech.candra.util.JwtUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class LoginService {
    @Inject
    Mailer mailer;
    @Transactional
    public Template login(LoginRequest loginRequest){
        UserAdmin byUserAdmin = UserAdmin.findbyUsername(loginRequest.getUsername());
        if (byUserAdmin==null){
            return new tech.candra.templates.Template(false, "user name not matches", null);
        }
        if(!loginRequest.getPassword().matches(byUserAdmin.getPassword())){
            return new tech.candra.templates.Template(false, "password is wrong", null);
        }

        if(byUserAdmin.getIsActive() == false){
            byUserAdmin.delete();
            return new tech.candra.templates.Template(false, "please activate your account and registry again", null);
        }
        System.out.println("create token");
        Session session = JwtUtil.generateToken(byUserAdmin);
        String token = JwtUtil.generateToken(byUserAdmin);
        return new tech.candra.templates.Template(true, "succes", token);
    }
}
