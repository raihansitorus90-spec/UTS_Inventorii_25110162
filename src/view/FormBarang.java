
package view;

import dao.BarangDAO;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok;
    private JComboBox<String> cmbKategori;
    private JTable tabel;
    private DefaultTableModel model;

    private final BarangDAO dao = new BarangDAO();

    public FormBarang() {
        setTitle("Sistem Inventori Barang Laboratorium");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buatForm();
        tampilkanData();
    }

    private void buatForm() {
        JPanel utama = new JPanel(new BorderLayout(10, 10));
        utama.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        utama.setBackground(new Color(245, 248, 252));

        JLabel judul = new JLabel("DATA BARANG LABORATORIUM");
        judul.setFont(new Font("Arial", Font.BOLD, 22));
        judul.setForeground(new Color(30, 70, 120));

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setOpaque(false);
        panelAtas.add(judul, BorderLayout.CENTER);

        JPanel panelInput = new JPanel(new GridLayout(4, 2, 10, 10));
        panelInput.setBorder(BorderFactory.createTitledBorder("Form Barang"));
        panelInput.setBackground(Color.WHITE);

        txtKode = new JTextField();
        txtNama = new JTextField();
        txtStok = new JTextField();

        cmbKategori = new JComboBox<>(new String[]{
            "Komputer", "Jaringan", "Alat Ukur"
        });

        panelInput.add(new JLabel("Kode Barang"));
        panelInput.add(txtKode);
        panelInput.add(new JLabel("Nama Barang"));
        panelInput.add(txtNama);
        panelInput.add(new JLabel("Kategori"));
        panelInput.add(cmbKategori);
        panelInput.add(new JLabel("Stok"));
        panelInput.add(txtStok);

        JButton btnSimpan = new JButton("Simpan");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnBersihkan = new JButton("Bersihkan");

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTombol.setOpaque(false);
        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnBersihkan);

        JPanel panelForm = new JPanel(new BorderLayout(5, 5));
        panelForm.setOpaque(false);
        panelForm.add(panelInput, BorderLayout.CENTER);
        panelForm.add(panelTombol, BorderLayout.SOUTH);

        model = new DefaultTableModel(
            new String[]{"Kode", "Nama Barang", "Kategori", "Stok"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabel = new JTable(model);
        tabel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabel.setRowHeight(24);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(BorderFactory.createTitledBorder("Daftar Barang"));

        utama.add(panelAtas, BorderLayout.NORTH);
        utama.add(panelForm, BorderLayout.CENTER);
        utama.add(scroll, BorderLayout.SOUTH);

        // Mengatur tinggi area tabel
        scroll.setPreferredSize(new Dimension(750, 220));

        add(utama);

        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBersihkan.addActionListener(e -> bersihkanForm());

        tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tabel.getSelectedRow();

                if (baris >= 0) {
                    txtKode.setText(model.getValueAt(baris, 0).toString());
                    txtNama.setText(model.getValueAt(baris, 1).toString());
                    cmbKategori.setSelectedItem(
                        model.getValueAt(baris, 2).toString()
                    );
                    txtStok.setText(model.getValueAt(baris, 3).toString());

                    txtKode.setEditable(false);
                }
            }
        });
    }

    private Barang ambilDataForm() {
        String kode = txtKode.getText().trim();
        String nama = txtNama.getText().trim();
        String stokText = txtStok.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();

        if (kode.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Kode dan nama barang tidak boleh kosong!");
            return null;
        }

        if (kode.length() > 10) {
            JOptionPane.showMessageDialog(this,
                "Kode barang maksimal 10 karakter!");
            return null;
        }

        int stok;
        try {
            stok = Integer.parseInt(stokText);
            if (stok < 0) {
                JOptionPane.showMessageDialog(this,
                    "Stok tidak boleh kurang dari 0!");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Stok harus berupa angka bulat >= 0!");
            return null;
        }

        return new Barang(kode, nama, kategori, stok);
    }

    private void simpanData() {
        Barang b = ambilDataForm();
        if (b == null) return;

        try {
            if (dao.simpan(b)) {
                JOptionPane.showMessageDialog(this,
                    "Data barang berhasil disimpan!");
                tampilkanData();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data gagal disimpan.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void ubahData() {
        Barang b = ambilDataForm();
        if (b == null) return;

        try {
            if (dao.ubah(b)) {
                JOptionPane.showMessageDialog(this,
                    "Data barang berhasil diubah!");
                tampilkanData();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data tidak ditemukan atau tidak berubah.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void hapusData() {
        String kode = txtKode.getText().trim();

        if (kode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Pilih barang yang akan dihapus!");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus barang dengan kode " + kode + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try {
            if (dao.hapus(kode)) {
                JOptionPane.showMessageDialog(this,
                    "Data barang berhasil dihapus!");
                tampilkanData();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Data tidak ditemukan.");
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal menghapus data: " + e.getMessage());
        }
    }

    private void tampilkanData() {
        model.setRowCount(0);

        try {
            List<Barang> daftar = dao.getAll();

            for (Barang b : daftar) {
                model.addRow(new Object[]{
                    b.getKodeBarang(),
                    b.getNamaBarang(),
                    b.getKategori(),
                    b.getStok()
                });
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                "Gagal menampilkan data: " + e.getMessage());
        }
    }

    private void bersihkanForm() {
        txtKode.setText("");
        txtNama.setText("");
        txtStok.setText("");
        cmbKategori.setSelectedIndex(0);
        txtKode.setEditable(true);
        tabel.clearSelection();
        txtKode.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormBarang().setVisible(true);
        });
    }
}
