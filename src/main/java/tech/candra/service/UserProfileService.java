package tech.candra.service;


import io.quarkus.logging.Log;
import lombok.extern.slf4j.Slf4j;
import tech.candra.dto.UserProfileRequest;
import tech.candra.models.UserAdmin;
import tech.candra.models.UserProfile;
import tech.candra.templates.Template;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@ApplicationScoped
@Slf4j
public class UserProfileService {
    @Transactional
    public Template post(UserProfileRequest userProfileRequest,String userAdminId){
        long id = Long.parseLong(userAdminId);

        //mencari data berdasarkan id
        UserAdmin byId = UserAdmin.findById(id);

        //validasi jika datanya kosong
        if (byId == null){
            Log.error("data dengan id tidak ditemukan");
            return new Template(false,"id tidak ditemukan ", null);
        }

        //mencari data berdasarkan user_admin_id
        UserProfile byUserAdminId = UserProfile.findByUserAdminId(id);
        //validasi jika id sudah digunakan
        if (byUserAdminId != null){
            Log.error("user profile dengan id "+id+" sudah ada");
            return new Template(false,"user profile dengan id "+id+" sudah digunakan", null);
        }


        //memanggil model userprofile
        UserProfile userProfile = new UserProfile();
        //set data baru untuk userprofile
        userProfile.setJenisKelamin(userProfileRequest.getJenisKelamin());
        userProfile.setTanggalLahir(userProfileRequest.getTanggalLahir());
        userProfile.setNomorTelepon(userProfileRequest.getNomorTelepon());
        userProfile.setAlamat(userProfileRequest.getAlamat());
        userProfile.setPendidikan(userProfileRequest.getPendidikanTerakhir());
        userProfile.setAgama(userProfileRequest.getAgama());
        userProfile.setCreateAt(LocalDateTime.now());
        userProfile.setUserAdminId(byId);

        //save data (post)
        UserProfile.persist(userProfile);

        log.info("data berhasil disimpan");

        return new Template(true, "succes", userProfile);

    }

    @Transactional
    public Template updateUserProfile(UserProfileRequest userProfileRequest, String userAdminId){

        //mengubah tipe data dari string ke long
        long id = Long.parseLong(userAdminId);
        //mencari profil berdasarkan user admin id
        UserProfile byUserAdminId = UserProfile.findByUserAdminId(id);
        //validasi ketika data yang dicari tidak ada, maka data tersebut tidak bisa diedit/di update
        if (byUserAdminId == null){
            return new Template(false, "data tidak ditemukan ", null);
        }

        //set data untuk user profile yang akan diedit
        byUserAdminId.setJenisKelamin(userProfileRequest.getJenisKelamin());
        byUserAdminId.setAlamat(userProfileRequest.getAlamat());
        byUserAdminId.setTanggalLahir(userProfileRequest.getTanggalLahir());
        byUserAdminId.setNomorTelepon(userProfileRequest.getNomorTelepon());
        byUserAdminId.setPendidikan(userProfileRequest.getPendidikanTerakhir());
        byUserAdminId.setAgama(userProfileRequest.getAgama());
        byUserAdminId.setUpdateAt(LocalDateTime.now());

        //save data (update)
        UserProfile.persist(byUserAdminId);

        log.info("data berhasil diupdate");

        return new Template(true,"succes", byUserAdminId);

    }

    @Transactional
    public Template deleteUserProfile(String userAdminId){
        //mengubah tipe data dari string ke long
        long id = Long.parseLong(userAdminId);
        //mencari profil berdasarkan user admin id
        UserProfile byUserAdminId = UserProfile.findByUserAdminId(id);
        //validasi ketika data yang dicari tidak ada, maka data tersebut tidak bisa diedit/di update
        if (byUserAdminId == null){
            return new Template(false, "data tidak ditemukan ", null);
        }
        byUserAdminId.delete();
        log.info("Data berhasil dihapus");
        return new Template(true, "user profile berhasil dihapus", null);

    }

    public Template getUserProfile(String userAdminId){
        //mengubah tipe data dari string ke long
        long id = Long.parseLong(userAdminId);
        //mencari profil berdasarkan user admin id
        UserProfile byUserAdminId = UserProfile.findByUserAdminId(id);
        //validasi ketika data yang dicari tidak ada, maka data tersebut tidak bisa diedit/di update
        if (byUserAdminId == null){
            return new Template(false, "data tidak ditemukan ", null);
        }

        return new Template(true, "sucess", byUserAdminId);

    }

}
