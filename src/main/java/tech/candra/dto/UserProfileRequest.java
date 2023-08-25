package tech.candra.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserProfileRequest {
    private String jenisKelamin;
    private Date tanggalLahir;
    private String alamat;
    private String nomorTelepon;
    private String agama;
    private String pendidikanTerakhir;


}
