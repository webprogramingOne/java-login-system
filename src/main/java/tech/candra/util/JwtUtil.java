package tech.candra.util;


import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import io.vertx.mutiny.ext.web.Session;
import org.hibernate.internal.SessionFactoryRegistry;
import tech.candra.dto.ConfirmationToken;
import tech.candra.dto.LoginRequest;
import tech.candra.models.UserAdmin;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;


public class JwtUtil {




    public static String generateToken(UserAdmin userAdmin) {
        String token = Jwt.issuer("xxxxx")
                .issuedAt(new Date().toInstant())
                .subject(userAdmin.getUsername())
                .groups("admin")
                .expiresIn(Duration.ofHours(1))
                .claim("username", userAdmin.getUsername())
                .claim("id",userAdmin.getId())
                .sign();
        return token;

    }




}
