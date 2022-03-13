
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.dao.PersistenciaJDBC;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JPanelAFuncionarioListagem extends JPanel implements ActionListener{

    private JPanelAFuncionario pnlAFuncionario;
    private Controle controle;

    private BorderLayout borderLayout;
    private JPanel pnlNorte;
    private JLabel lblFiltro;
    private JTextField txfFiltro;
    private JButton btnFiltro;

    private JPanel pnlCentro;
    private JScrollPane scpListagem;
    private JTable tblListagem;
    private DefaultTableModel modeloTabela;

    private JPanel pnlSul;
    private JButton btnNovo;
    private JButton btnAlterar;
    private JButton btnRemover;

    private Funcionario funcionario;
    private SimpleDateFormat format;


    public JPanelAFuncionarioListagem(JPanelAFuncionario pnlAFuncionario, Controle controle){

        this.pnlAFuncionario = pnlAFuncionario;
        this.controle = controle;

        initComponents();
    }

    public void populaTable(){

        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel();//recuperacao do modelo da tabela

        model.setRowCount(0);//elimina as linhas existentes (reset na tabela)

        try {

            List<Funcionario> listFuncionarios = controle.getConexaoJDBC().listFuncionarios();
            for(Funcionario f : listFuncionarios){

                model.addRow(new Object[]{f.getCpf(), f.getNome(), f.getEmail(), f.getRg(), f.getEndereco(), format.format(f.getData_cadastro().getTime()), f.getNumero_ctps(), f.getCargo(), f.getNumero_pis()});
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Funcionarios -:"+ex.getLocalizedMessage(), "Funcionarios", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

    }

    private void initComponents(){

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);//seta o gerenciado border para este painel

        pnlNorte = new JPanel();
        pnlNorte.setLayout(new FlowLayout());

        lblFiltro = new JLabel("Filtrar por Nome:");
        pnlNorte.add(lblFiltro);

        txfFiltro = new JTextField(20);
        pnlNorte.add(txfFiltro);

        btnFiltro = new JButton("Filtrar");
        btnFiltro.addActionListener(this);
        btnFiltro.setFocusable(true);    //acessibilidade
        btnFiltro.setToolTipText("btnFiltrar"); //acessibilidade
        btnFiltro.setActionCommand("botao_filtro");
        pnlNorte.add(btnFiltro);

        this.add(pnlNorte, BorderLayout.NORTH);//adiciona o painel na posicao norte.

        pnlCentro = new JPanel();
        pnlCentro.setLayout(new BorderLayout());

        scpListagem = new JScrollPane();
        tblListagem =  new JTable();

        modeloTabela = new DefaultTableModel(
                new String [] {
                        "CPF", "Nome", "Email", "RG", "Endereço", "Data Cadastro", "CTPS", "Cargo", "PIS"
                }, 0);

        tblListagem.setModel(modeloTabela);
        scpListagem.setViewportView(tblListagem);

        pnlCentro.add(scpListagem, BorderLayout.CENTER);

        this.add(pnlCentro, BorderLayout.CENTER);//adiciona o painel na posicao norte.

        this.filtragemTabela();

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setFocusable(true);    //acessibilidade
        btnNovo.setToolTipText("btnNovo"); //acessibilidade
        btnNovo.setMnemonic(KeyEvent.VK_N);
        btnNovo.setActionCommand("botao_novo");

        pnlSul.add(btnNovo);

        btnAlterar = new JButton("Editar");
        btnAlterar.addActionListener(this);
        btnAlterar.setFocusable(true);    //acessibilidade
        btnAlterar.setToolTipText("btnAlterar"); //acessibilidade
        btnAlterar.setActionCommand("botao_alterar");

        pnlSul.add(btnAlterar);

        btnRemover = new JButton("Remover");
        btnRemover.addActionListener(this);
        btnRemover.setFocusable(true);    //acessibilidade
        btnRemover.setToolTipText("btnRemover"); //acessibilidade
        btnRemover.setActionCommand("botao_remover");

        pnlSul.add(btnRemover);//adiciona o botao na fila organizada pelo flowlayout


        this.add(pnlSul, BorderLayout.SOUTH);//adiciona o painel na posicao norte.

        format = new SimpleDateFormat("dd/MM/yyyy");

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {


        if(arg0.getActionCommand().equals(btnNovo.getActionCommand())){

            pnlAFuncionario.showTela("tela_funcionario_formulario");

            pnlAFuncionario.getFormulario().setFuncionarioFormulario(null); //limpando o formulário.

        }else if(arg0.getActionCommand().equals(btnAlterar.getActionCommand())){
            int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){
                try {
                    PersistenciaJDBC persistencia = new PersistenciaJDBC();
                    Funcionario f = new Funcionario();

                    DefaultTableModel model = (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table
                    Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada
                    f = (Funcionario) persistencia.find(f.getClass(), linha.get(0));
                    pnlAFuncionario.showTela("tela_funcionario_formulario");
                    pnlAFuncionario.getFormulario().setFuncionarioFormulario(f);
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Erro ao editar Funcionario -:"+ex.getLocalizedMessage(), "Funcionario", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
            }else if(arg0.getActionCommand().equals(btnRemover.getActionCommand())){


                int indice = tblListagem.getSelectedRow();//recupera a linha selecionada
                if(indice > -1){

                    try {

                        DefaultTableModel model =  (DefaultTableModel) tblListagem.getModel(); //recuperacao do modelo da table

                        Vector linha = (Vector) model.getDataVector().get(indice);//recupera o vetor de dados da linha selecionada

                        String s = (String) linha.get(0);

                        Funcionario f = new Funcionario();
                        PersistenciaJDBC persistencia = new PersistenciaJDBC();

                        f = (Funcionario) persistencia.find(f.getClass(), s);

                        pnlAFuncionario.getControle().getConexaoJDBC().remover(f);
                        JOptionPane.showMessageDialog(this, "Funcionario removido!", "Funcionario", JOptionPane.INFORMATION_MESSAGE);
                        populaTable(); //refresh na tabela

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao remover Funcionario -:"+ex.getLocalizedMessage(), "Funcionarios", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }

                }else
                    JOptionPane.showMessageDialog(this, "Selecione uma linha para remover!", "Remoção", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void filtragemTabela(){
        final TableRowSorter<TableModel> ordenador = new TableRowSorter<TableModel>(modeloTabela);
        tblListagem.setRowSorter(ordenador);
        btnFiltro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = txfFiltro.getText();
                if(text.length() == 0) {
                    ordenador.setRowFilter(null);
                } else {
                    try {
                        ordenador.setRowFilter(RowFilter.regexFilter(text, 1));
                    } catch(PatternSyntaxException pse) {
                        System.out.println("Erro!");
                    }
                }
            }
        });
    }

}