package form;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sun.security.util.Length;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Wiwit Agus Triyanto
 */
public class IfrmBuku_NIM extends javax.swing.JInternalFrame {
    String sql; //deklarasi variabel sql untuk perintah query
    
    private static IfrmBuku_NIM wujudForm;
    
    public static IfrmBuku_NIM cekForm() {  //method agar form tidak muncul dobel
	if (wujudForm == null){
            wujudForm = new IfrmBuku_NIM();
	}
	return wujudForm;
    }
    
    void tampilData(String aksi){   //digunakan untuk menampilkan data ke jTable | parameter aksi berisi nilai ketika dipanggil
        final String[] headers = {"KODE BUKU", "JUDUL BUKU", "PENULIS BUKU", "PENERBIT BUKU"};  //membuat header jTable
        DefaultTableModel modelTabel = new DefaultTableModel(null, headers);
                
        String kataKunci = txtKataKunci.getText();  //variabel katakunci untuk menampung inputan txtKataKunci
        String kategoriCari = "";   //variabel kategoriCari untuk menampung nama field setelah dipilih pilihan dari cboKategori
        
        if (cboKategori.getSelectedItem()=="Kode Buku"){    //jika cboKategori dipilih Kode Buku maka...
            kategoriCari = "kode_buku"; //variable kategoriCari diisi kode_buku
        }else if (cboKategori.getSelectedItem()=="Judul Buku"){ //jika cboKategori dipilih Judul Buku maka...
            kategoriCari = "judul_buku";    //variable kategoriCari diisi judul_buku
        }else if (cboKategori.getSelectedItem()=="Penulis Buku"){   //jika cboKategori dipilih Penulis Buku maka...
            kategoriCari = "penulis_buku";  //variable kategoriCari diisi penulis_buku
        }else if (cboKategori.getSelectedItem()=="Penerbit Buku"){  //jika cboKategori dipilih Penerbit Buku maka...
            kategoriCari = "penerbit_buku"; //variable kategoriCari diisi penerbit_buku
        }
        
        try{
            if (aksi=="cari"){  //jika nilai untuk parameter aksi == cari maka...
                sql = "SELECT * FROM tb_buku WHERE " + kategoriCari + " LIKE '%"+kataKunci+"%'";    //lakukan perintah query mencari data buku sesuai kategoriCari dan kataKunci
            }else{  //jika nilai parameter aksi kosong maka...
                sql = "SELECT * FROM tb_buku";  //lakukan perintah menampilkan semua data buku
            }
            ResultSet rs = koneksiDB.executeQuery(sql); //eksekusi query diatas
                        
            while(rs.next()){   //perulangan dari hasil query 
                String[] data = {
                    rs.getString("kode_buku"),  //ambil data dari field kode_buku
                    rs.getString("judul_buku"), //ambil data dari field judul_buku
                    rs.getString("penulis_buku"),   //ambil data dari field penulis_buku
                    rs.getString("penerbit_buku")   //ambil data dari field penerbit_buku
                };
                modelTabel.addRow(data);
            }
            
            rs.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "ERROR: \n" + ex.toString(), 
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);    //menampilkan pesan error jika kondisi di dalam blok try tidak berjalan normal
        }
        jtblBuku.setModel(modelTabel);  //menampilkan data dari hasil query ke jtblBuku
    }
    
    void simpanData(){  //digunakan untuk menyimpan data
        //query untuk menyimpan data ke tb_buku
        sql = "INSERT INTO tb_buku (kode_buku,judul_buku,penulis_buku,penerbit_buku) VALUES ("
                + "'" + txtKodeBuku.getText() + "',"
                + "'" + txtJudulBuku.getText() + "',"
                + "'" + txtPenulisBuku.getText() + "',"
                + "'" + txtPenerbitBuku.getText() + "')";
        int status = koneksiDB.execute(sql);    //eksekusi query diatas
        if (status == 1) {  //jika eksekusi query berhasil maka...
            JOptionPane.showMessageDialog(this, "Simpan Berhasil", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //menampilkan pesan simpan berhasil
        }else{  //jika eksekusi query gagal maka...
            JOptionPane.showMessageDialog(this, "Simpan Gagal", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //meanmpilkan pesan simpan gagal
        }
    }

    void ubahData(){    //digunakan untuk mengubah data
        //query untuk mengubah data ke tb_buku
        sql = "UPDATE tb_buku SET "
                + "judul_buku='" + txtJudulBuku.getText() + "',"
                + "penulis_buku='" + txtPenulisBuku.getText() + "',"
                + "penerbit_buku='" + txtPenerbitBuku.getText() + "'"
                + " WHERE kode_buku='" + txtKodeBuku.getText() + "'";
        int status = koneksiDB.execute(sql);    //eksekusi query diatas
        if (status == 1) {  //jika eksekusi query berhasil maka...
            JOptionPane.showMessageDialog(this, "Ubah Berhasil", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //menampilkan pesan ubah berhasil
        }else{  //jika eksekusi query gagal maka...
            JOptionPane.showMessageDialog(this, "Ubah Gagal", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //meanmpilkan pesan ubah gagal
        }  
    }

    void hapusData(){   //digunakan untuk menghapus data
         //query untuk mengubah data ke tb_buku
        sql = "DELETE FROM tb_buku WHERE kode_buku='" + txtKodeBuku.getText() + "'";
        int status = koneksiDB.execute(sql);    //eksekusi query diatas
        if (status == 1) {  //jika eksekusi query berhasil maka...
            JOptionPane.showMessageDialog(this, "Hapus Berhasil", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //menampilkan pesan hapus berhasil
        }else{  //jika eksekusi query gagal maka...
            JOptionPane.showMessageDialog(this, "Hapus Gagal", 
                "Informasi", JOptionPane.INFORMATION_MESSAGE);  //meanmpilkan pesan hapus gagal
        }
    }
    
    void bersih(){  //digunakan untuk membersihkan jTextField
        txtKodeBuku.setText("");
        txtJudulBuku.setText("");
        txtPenulisBuku.setText("");
        txtPenerbitBuku.setText("");
        txtKataKunci.setText("");
    }
    
    void aktifInput(Boolean status){    //digunakan untuk enable/disable jTextField sesuai statusnya ketika dipanggil
        txtKodeBuku.setEnabled(status);
        txtJudulBuku.setEnabled(status);
        txtPenulisBuku.setEnabled(status);
        txtPenerbitBuku.setEnabled(status);
    }
    
    void aktifTombol(Boolean status){   //digunakan untuk enable/disable jButton sesuai statusnya ketika dipanggil
        btnSimpan.setEnabled(status);
        btnUbah.setEnabled(status);
        btnHapus.setEnabled(status);
    }
    
    void kodeOtomatis(){    //digunakan untuk membuat kode otomatis
        //query untuk mengambil 3 karakter yang dimulai dari karakter ke-3
        //pada field kode_buku dari tabel tb_buku dan diurutkan dari nilai yang terbesar
        sql = "SELECT SUBSTRING(kode_buku, 3, 3) AS kode FROM tb_buku ORDER BY kode_buku DESC";
        ResultSet rs = koneksiDB.executeQuery(sql);
        
        try {
            if (rs.next()){ //jika data ada maka...
                int IDlama = rs.getInt("kode");
                int IDbaru = IDlama + 1;
                String nol = "";
                
                if (Integer.toString(IDbaru).length() == 1){    //jika panjang IDbaru 1 karakter maka..
                    nol = "00";
                }else if (Integer.toString(IDbaru).length() == 2){  //jika panjang IDbaru 2 karakter maka..
                    nol = "0";
                }else if (Integer.toString(IDbaru).length() == 3){  //jika panjang IDbaru 3 karakter maka..
                    nol = "";
                }
                
                txtKodeBuku.setText("B-" + nol + IDbaru);   //isi dari txtKodeBuku merupakan gabungan dari "B-" , nilai "nol" , dan nilai "IDbaru"
            }else{
                txtKodeBuku.setText("B-001");   //jika data masih kosong maka awali dengan kode "B-001"
            }
        } catch (SQLException ex) {
            Logger.getLogger(IfrmBuku_NIM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    /**
     * Creates new form IfrmAnak1
     */
    public IfrmBuku_NIM() {
        initComponents();
        
        tampilData(""); //menampilkan data
        bersih();   //membersihkan jTextField
        
        aktifInput(false);  //menonaktifkan jTextField yang ada pada aktifInput
        aktifTombol(false); //menonaktifkan jButton yang ada pada aktifInput
        btnTambah.setEnabled(true);
        
        cboKategori.removeAllItems();   //membersihkan item cboKategori
        //menambahkan item pada cboKategori
        cboKategori.addItem("Kode Buku");
        cboKategori.addItem("Judul Buku");
        cboKategori.addItem("Penulis Buku");
        cboKategori.addItem("Penerbit Buku");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKodeBuku = new javax.swing.JTextField();
        txtJudulBuku = new javax.swing.JTextField();
        txtPenulisBuku = new javax.swing.JTextField();
        txtPenerbitBuku = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboKategori = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtKataKunci = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblBuku = new javax.swing.JTable();

        setClosable(true);
        setTitle("Data Buku _ NIM");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("DATA BUKU");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(21, 21, 21))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setText("Kode Buku");

        jLabel3.setText("Judul Buku");

        jLabel4.setText("Penulis Buku");

        jLabel5.setText("Penerbit Buku");

        txtKodeBuku.setText("jTextField1");

        txtJudulBuku.setText("jTextField2");

        txtPenulisBuku.setText("jTextField3");

        txtPenerbitBuku.setText("jTextField4");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pencarian Data"));

        jLabel6.setText("Kategori");

        cboKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Kata Kunci");

        txtKataKunci.setText("jTextField5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKataKunci, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtKataKunci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtKodeBuku)
                    .addComponent(txtPenerbitBuku)
                    .addComponent(txtPenulisBuku)
                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKodeBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPenulisBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPenerbitBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/10090401.png"))); // NOI18N
        btnTambah.setText("TAMBAH");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambah.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50001.png"))); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSimpan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50003.png"))); // NOI18N
        btnUbah.setText("UBAH");
        btnUbah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUbah.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50002.png"))); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHapus.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50007.png"))); // NOI18N
        btnCari.setText("CARI");
        btnCari.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCari.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50005.png"))); // NOI18N
        btnRefresh.setText("REFRESH");
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(btnCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtblBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtblBuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblBukuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblBuku);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        tampilData("cari");
        bersih();
        aktifInput(false);
        aktifTombol(false);
        btnTambah.setText("TAMBAH");
    }//GEN-LAST:event_btnCariActionPerformed

    private void jtblBukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblBukuMouseClicked
        // TODO add your handling code here:
        try {          
            //mengisi jTextField sesuai baris yang di klik pada jtblBuku | 0 - sekian, mewakili kolom
            txtKodeBuku.setText(jtblBuku.getValueAt(jtblBuku.getSelectedRow(), 0).toString());
            txtJudulBuku.setText(jtblBuku.getValueAt(jtblBuku.getSelectedRow(), 1).toString());
            txtPenulisBuku.setText(jtblBuku.getValueAt(jtblBuku.getSelectedRow(), 2).toString());
            txtPenerbitBuku.setText(jtblBuku.getValueAt(jtblBuku.getSelectedRow(), 3).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
        
        aktifInput(true);
        aktifTombol(true);
        txtKodeBuku.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnTambah.setText("BATAL");
    }//GEN-LAST:event_jtblBukuMouseClicked

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        tampilData("");
        bersih();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        simpanData();
        tampilData("");
        bersih();
        aktifInput(false);
        aktifTombol(false);
        
        btnTambah.setText("TAMBAH");
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int hapus = JOptionPane.showConfirmDialog(this, "Anda yakin menghapus data " +
                txtKodeBuku.getText() + " ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (hapus==0){  //jika pilih yes
            hapusData();
            tampilData("");
            bersih();
            aktifInput(false);
            aktifTombol(false);
            
            btnTambah.setText("TAMBAH");
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahData();
        tampilData("");
        bersih();
        aktifInput(false);
        aktifTombol(false);
        
        btnTambah.setText("TAMBAH");
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        bersih();
        aktifInput(true);
        aktifTombol(true);
        btnUbah.setEnabled(false);
        btnHapus.setEnabled(false);
        
        if (btnTambah.getText().equals("BATAL")){   //jika text pada btnTAMBAH="BATAL" maka..
            btnTambah.setText("TAMBAH");    //ubah text pada btnTAMBAH menjadi "TAMBAH"
            
            aktifInput(false);
            aktifTombol(false);
            btnTambah.setEnabled(true);
        }else{  //jika text pada btnTAMBAH selain BATAL maka..
            btnTambah.setText("BATAL"); //ubah text pada btnTAMBAH menjadi "BATAL"
            txtKodeBuku.setEnabled(false);
            kodeOtomatis();
        }
    }//GEN-LAST:event_btnTambahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JComboBox<String> cboKategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtblBuku;
    private javax.swing.JTextField txtJudulBuku;
    private javax.swing.JTextField txtKataKunci;
    private javax.swing.JTextField txtKodeBuku;
    private javax.swing.JTextField txtPenerbitBuku;
    private javax.swing.JTextField txtPenulisBuku;
    // End of variables declaration//GEN-END:variables
}
