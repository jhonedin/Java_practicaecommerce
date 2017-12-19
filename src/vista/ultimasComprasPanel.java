/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import logica.CompraLogica;
import modelo.Compra;
import modelo.Usuario;

/**
 *
 * @author Jhon
 */
public class ultimasComprasPanel extends javax.swing.JPanel {

    FramePrincipal frame;
    DefaultTableModel modelo;
    public ultimasComprasPanel(FramePrincipal frame) {
        this.frame = frame;
        initComponents();
        setBackground(Color.white);
    }

    public void llenarTablaCompras(Usuario usur){
        CompraLogica compraLogica = new CompraLogica();
        int idUsuario = usur.getUsuarioID();
        List<Compra> listaCompra = compraLogica.listaComprasDeUsuario(idUsuario);
        setTabla(listaCompra);        
    }    
    
    public void setTabla(List<Compra> CompraUsuario){
        modelo = (DefaultTableModel) comprasRelizadasTable.getModel();
        int size = modelo.getRowCount();
        for (int i = 0; i < size; i++) {
            modelo.removeRow(0);
        } 
        String aux[];
        for (int j = 0; j < CompraUsuario.size(); j++) {
            aux = new String[6];
            aux[0] = CompraUsuario.get(j).getCompraID().toString();
            aux[1] = CompraUsuario.get(j).getProductoID().getProductoID().toString();
            aux[2] = CompraUsuario.get(j).getProductoID().getNombreProducto();
            aux[3] = String.valueOf(CompraUsuario.get(j).getProductoID().getCantidadDisponible());
            aux[4] = String.valueOf(CompraUsuario.get(j).getProductoID().getPrecioProducto());
            aux[5] = CompraUsuario.get(j).getFechaCompra().toString();      
            modelo.addRow(aux);
         }
    }      

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comprasRelizadasTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        volverButton = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("TUS ULTIMAS COMPRAS REALIZADAS");

        comprasRelizadasTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Compra", "ID_Producto", "Nombre", "Cantidad", "Valor", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(comprasRelizadasTable);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Bestshop_Logo2.jpg"))); // NOI18N

        volverButton.setText("Volver");
        volverButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(volverButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(77, 77, 77)
                        .addComponent(volverButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(107, 107, 107))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void volverButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverButtonActionPerformed
        frame.swap(1); // volver al panel de cuenta de usuario
    }//GEN-LAST:event_volverButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable comprasRelizadasTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton volverButton;
    // End of variables declaration//GEN-END:variables
}
