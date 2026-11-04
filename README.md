# UTS_Inventori_25110162

1. Pembuatan Project NetBeans
Pada tahap pertama, dibuat project Java NetBeans dengan nama UTS_Inventori_NIM. Project menggunakan konsep MVC (Model, View, Controller/DAO) dengan memisahkan setiap bagian ke dalam paket agar program lebih terstruktur.
2. Pembuatan Class Koneksi
Pada tahap kedua, dibuat class Koneksi yang berfungsi menghubungkan aplikasi Java dengan database MySQL bernama db_inventori. Jika koneksi gagal, program akan menampilkan pesan kesalahan agar mudah diketahui.
3. Pembuatan Class Model Barang
Pada tahap ketiga, dibuat class Barang yang memiliki empat atribut, yaitu kode barang, nama barang, kategori, dan stok. Setiap atribut menggunakan access modifier private serta dilengkapi constructor, getter, dan setter.
4. Pembuatan Form Barang
Pada tahap keempat, dibuat tampilan FormBarang menggunakan Java Swing. Form terdiri dari JTextField untuk memasukkan kode, nama, dan stok barang, JComboBox untuk memilih kategori, tombol Simpan, Ubah, Hapus, Bersihkan, serta JTable untuk menampilkan daftar barang.
5. Proses Simpan Data
Pada tahap kelima, dibuat fungsi tombol Simpan untuk memasukkan data barang ke database. Sebelum disimpan, program melakukan validasi agar kode dan nama tidak kosong serta stok harus berupa angka minimal 0. Proses penyimpanan menggunakan PreparedStatement.
6. Menampilkan dan Memilih Data
Pada tahap keenam, data barang ditampilkan ke dalam JTable ketika form dibuka dan setelah data berhasil disimpan. Ketika salah satu baris tabel diklik, data barang akan ditampilkan kembali ke dalam form agar dapat dipilih untuk proses berikutnya.
7. Proses Ubah dan Hapus Data
Pada tahap ketujuh, dibuat fungsi tombol Ubah dan Hapus berdasarkan kode barang. Tombol Ubah digunakan untuk memperbarui data yang sudah ada, sedangkan tombol Hapus digunakan untuk menghapus data dengan menampilkan konfirmasi terlebih dahulu sebelum penghapusan dilakukan.

