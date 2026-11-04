
package dao;

import model.Barang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {

    public boolean simpan(Barang b) {
        String sql = "INSERT INTO barang VALUES (?, ?, ?, ?)";

        try (Connection c = Koneksi.getConnection()) {
            if (c == null) return false;

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, b.getKodeBarang());
                ps.setString(2, b.getNamaBarang());
                ps.setString(3, b.getKategori());
                ps.setInt(4, b.getStok());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean ubah(Barang b) {
        String sql = "UPDATE barang SET nama_barang=?, "
                   + "kategori=?, stok=? WHERE kode_barang=?";

        try (Connection c = Koneksi.getConnection()) {
            if (c == null) return false;

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, b.getNamaBarang());
                ps.setString(2, b.getKategori());
                ps.setInt(3, b.getStok());
                ps.setString(4, b.getKodeBarang());

                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean hapus(String kode) {
        String sql = "DELETE FROM barang WHERE kode_barang=?";

        try (Connection c = Koneksi.getConnection()) {
            if (c == null) return false;

            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, kode);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Barang> getAll() {
        List<Barang> daftar = new ArrayList<>();
        String sql = "SELECT * FROM barang ORDER BY kode_barang";

        try (Connection c = Koneksi.getConnection()) {
            if (c == null) return daftar;

            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Barang b = new Barang(
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("kategori"),
                        rs.getInt("stok")
                    );
                    daftar.add(b);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return daftar;
    }
}
