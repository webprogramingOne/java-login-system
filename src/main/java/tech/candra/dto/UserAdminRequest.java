package tech.candra.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAdminRequest {
    private String username;
    private String password;
    private String namaLengkap;
    private String email;
    @JsonIgnore
    private  String token;
    @JsonIgnore
    private Boolean isActive;


}
