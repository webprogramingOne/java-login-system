# **INFORMASI PROJEK**

> ## Projek Back-End Kawah Edukasi Batch IV

![ERD Picture](Entitas.png) 

Bahasa Pemrograman: __*JAVA version 11.0.15*__

Framework : __*Quarkus version 2.12.3*__

DBMS : __*POSTGRESQL*__

IDE : __*IntelliJ IDEA Community Edition, Visual Studio Code*__


> ## Dependencies
✬ quarkus-hibernate-orm-panache                      
✬ quarkus-hibernate-validator                        
✬ quarkus-jdbc-postgresql                            
✬ quarkus-resteasy-reactive                          
✬ quarkus-resteasy-reactive-jackson   
✬ quarkus-smallrye-openapi
✬ org.projectlombok
✬ quarkus-smallrye-jwt
✬ quarkus-smallrye-jwt-build
✬ rest-assured


> ## Entity 

- ### user_admin, atribut: 
    - id
    - create_at 
    - email
    - nama_lengkap
    - password
    - update_at
    - username


- ### user_admin, atribut: 
    - id
    - agama
    - alamat
    - create_at 
    - jenis_kelamin
    - nomot_telepon
    - pendidikan
    - tanggal_lahir
    - update_at
    
   
- ### Jurusan, atribut: 
    - id
    - namaJurusan *(wajib diisi)*
    - jenjang
  
- ### Dosen, atribut:
    - id
    - nip
    - alamat
    - jenis_kelamin
    - nama
    - notlp
  
- ### Mahasiswa, atribut:
    - id
    - nama *(wajib diisi)*
    - jenisKelamin *(wajib diisi)*
    - noTlp *(wajib diisi)*
    - alamat *(wajib diisi)*


- ### MataKuliah, atribut:
    - id
    - namaMataKuliah *(wajib diisi)*
    - sks *(wajib diisi)*


> ## Relasi Antar Entitas / Assocision
- One To One : user_admin -> user_profile
- Many To One : Mahasiswa -> Jurusan
- Many To Many : Mahasiswa -> MataKuliah
- One To Many : Dosen -> MataKuliah

> ## Format Response 
- Status true *(Boolean)*
- Pesan berhasil *(String)*
- Data yang dikembalikan *(Object)*


