package tech.candra.templates;

import tech.candra.dto.UserAdminRequest;
import tech.candra.util.RegexUtil;

public class ValidasiTemplate {
    public static Template validasiUserAdminRequest(UserAdminRequest userAdminRequest) {
        Boolean namaLengkap = RegexUtil.isAlphaOnly(userAdminRequest.getNamaLengkap());
        if (!namaLengkap) {
            return new Template(false, "Nama Lengkap Harus Alphabeth", userAdminRequest.getNamaLengkap());
        }
        Boolean email = RegexUtil.isEmail(userAdminRequest.getEmail());
        if(!email){
            return new Template(false, "Email harus sesuai format", userAdminRequest.getEmail());
        }
        return null;
    }
}
