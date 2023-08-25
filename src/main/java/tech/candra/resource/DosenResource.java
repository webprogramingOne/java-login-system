package tech.candra.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import tech.candra.models.Dosen;
import tech.candra.models.MataKuliah;
import tech.candra.templates.Template;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("dosen")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DosenResource {


    @GET
    @Operation(summary = "Get All Dosen Data's", description = "Tampilkan Semua data Dosen")
    public Response getAll(){
        List<Dosen> dosen = Dosen.listAll();
        if (dosen.isEmpty()) {
            return Response.ok(new Template (false, "Data Dosen tidak ditemukan", dosen))
                    .status(Response.Status.OK).build();
        }
            return Response.ok(new Template (true, "Data Dosen berhasil ditemukan", dosen))
                .status(Response.Status.OK).build();
    }

    @GET
    @Path("{id}")
    @Operation(summary = "Get Dosen By ID", description = "Mendapatkan data dosen berdasarkan ID")
    public Response getDosenById(@PathParam("id") Long id){
        Dosen dosen = Dosen.findById(id);
        if (dosen == null) {
            return Response.ok(new Template(false, "ID Dosen Tidak ditemukan",dosen))
                    .status(Response.Status.OK).build();
        }
            return Response.ok(new Template(true, "ID Dosen berhasil ditemukan",dosen))
                .status(Response.Status.OK).build();

    }


    @POST
    @Transactional
    @Operation(summary = "Add New Dosen Data's", description = "menambahkan data dosen baru")
    public Template addDosen(@Valid Dosen dosen) {
        dosen.persist();
        for (MataKuliah mataKuliah : dosen.mataKuliahList) {
            mataKuliah.persist();
        }
        return new Template (true, "Data dosen berhasil di tambahkan", dosen );
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update Dosen Data's", description = "Merubah data Dosen")
    public Response updateDosen(@PathParam("id")Long id, @Valid Dosen newDosen){
        Dosen oldDosen = Dosen.findById(id);
        if (oldDosen == null){
            return Response.ok(new Template(true, "Data Dosen tidak ditemukan", oldDosen))
                    .status(Response.Status.OK).build();
        }
        oldDosen.nip = newDosen.nip;
        oldDosen.nama = newDosen.nama;
        oldDosen.jenisKelamin = newDosen.jenisKelamin;
        oldDosen.noTlp = newDosen.noTlp;
        oldDosen.alamat = newDosen.alamat;
        return Response.ok(new Template(false, "Data Dosen berhasil diubah", oldDosen))
                .status(Response.Status.OK).build();
    }


    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete Dosen Data's By ID", description = "Menghapus data dosen dengan ID")
    public Response deleteDosenById(@PathParam("id")Long id){
        Boolean delete = Dosen.deleteById(id);
        List<Dosen> dosen = Dosen.findById(id);
        if (delete){
            return Response.ok(new Template(true,"Data Dosen berhasil dihapus" , dosen))
                    .status(Response.Status.OK).build();
        }
            return Response.ok(new Template(false, "Data Dosen tidak ditemukan" ,dosen))
                .status(Response.Status.OK).build();

    }

}
