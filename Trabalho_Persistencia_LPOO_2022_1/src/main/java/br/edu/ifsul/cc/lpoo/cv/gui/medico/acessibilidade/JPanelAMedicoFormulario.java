package br.edu.ifsul.cc.lpoo.cv.gui.medico.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import br.edu.ifsul.cc.lpoo.cv.model.Funcionario;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import br.edu.ifsul.cc.lpoo.cv.model.Cargo;
import br.edu.ifsul.cc.lpoo.cv.util.Util;

import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

public class JPanelAMedicoFormulario extends JPanel implements ActionListener{


    private JPanelAMedico pnlAMedico;
    private Controle controle;

    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;

    private JPanel pnlDadosCadastrais;
    private JPanel pnlCentroDadosCadastrais;

    private Medico medico;
    private SimpleDateFormat format;

    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblNickname;
    private JTextField txfNickname;

    private JLabel lblSenha;
    private JPasswordField txfSenha;

    private JLabel lblCpf;
    private JTextField txfCpf;

    private JLabel lblCep;
    private JTextField txfCep;

    private JLabel lblComplemento;
    private JTextField txfComplemento;

    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;

    private JLabel lblDataNascimento;
    private JTextField txfDataNascimento;

    private JLabel lblEmail;
    private JTextField txfEmail;

    private JLabel lblEndereco;
    private JTextField txfEndereco;

    private JLabel lblNumeroCelular;
    private JTextField txfNumeroCelular;

    private JLabel lblRg;
    private JTextField txfRg;

    private JLabel lblCrmv;
    private JTextField txfCrmv;

    private JPanel pnlSul;
    private JPanel pnlCentro;
    private JButton btnGravar;
    private JButton btnCancelar;

    private JPanel pnlDadosAgenda;

    private JLabel lblCargo;
    private JComboBox cbxCargo;

    public JPanelAMedicoFormulario(JPanelAMedico pnlAMedico, Controle controle){

        this.pnlAMedico = pnlAMedico;
        this.controle = controle;

        initComponents();

    }


    public Medico getMedicobyFormulario() {
        //validacao do formulario

        String mensagemErro = "";

        if(txfNickname.getText().trim().length() < 4)
            mensagemErro += "Nome Invalido\n";

        else if(new String(txfSenha.getPassword()).trim().length() < 4){
            mensagemErro += "Senha inválida\n";

        }else if(txfCpf.getText().trim().length() != 11){
            mensagemErro += "Cpf invalido\n";

        }else if(txfEmail.getText().trim().length() < 8){
            mensagemErro += "Email invalido\n";

        }else if(txfNumeroCelular.getText().trim().length() < 9){
            mensagemErro += "Telefone invalido\n";

        }else if(txfRg.getText().trim().length() < 6){
            mensagemErro += "RG invalido\n";

        }else if(txfCrmv.getText().trim().length() < 4){
            mensagemErro += "CTPS invalido\n";
        }

        if(mensagemErro != ""){
            JOptionPane.showMessageDialog(this, mensagemErro);
        }else{
            Medico m = new Medico();
            m.setNome(txfNickname.getText().trim());
            m.setSenha(new String(txfSenha.getPassword()).trim());
            m.setNumero_crmv(txfCrmv.getText().trim());
            m.setCpf(txfCpf.getText().trim());
            m.setCep(txfCep.getText().trim());
            m.setComplemento(txfComplemento.getText().trim());
            m.setEmail(txfEmail.getText().trim());
            m.setEndereco(txfEndereco.getText().trim());
            m.setNumero_celular(txfNumeroCelular.getText().trim());
            m.setRg(txfRg.getText().trim());
            Calendar data_nasc = Calendar.getInstance();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            try {
                data_nasc.setTime(formato.parse(txfDataNascimento.getText().trim()));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Data de Nascimento inválida");
                ex.printStackTrace();
                return null;
            }

            if (medico != null)
                m.setData_cadastro(medico.getData_cadastro());

            m.setData_nascimento(data_nasc);

            return m;
        }
        return null;
    }


    public void setMedicoFormulario(Medico m) {

        if (m == null) {//se o parametro estiver nullo, limpa o formulario
            txfNickname.setText("");
            txfSenha.setText("");
            txfCrmv.setText("");
            txfCpf.setText("");
            txfCep.setText("");
            txfRg.setText("");
            txfComplemento.setText("");
            txfDataCadastro.setText("");
            txfDataNascimento.setText("");
            txfEmail.setText("");
            txfEndereco.setText("");
            txfNumeroCelular.setText("");

            txfCpf.setEditable(true);
            txfDataCadastro.setEditable(false);

            medico = null;
        } else {
            medico = m;
            txfNickname.setText(medico.getNome());
            txfSenha.setText(medico.getSenha());
            txfCrmv.setText(medico.getNumero_crmv());
            txfCpf.setText(medico.getCpf());
            txfCpf.setEditable(false);
            txfRg.setText(medico.getRg());
            txfCep.setText(medico.getCep());
            txfComplemento.setText(medico.getComplemento());
            txfDataCadastro.setText(format.format(medico.getData_cadastro().getTime()));
            txfDataCadastro.setEditable(false);
            txfDataNascimento.setText(format.format(medico.getData_nascimento().getTime()));
            txfEmail.setText(medico.getEmail());
            txfEndereco.setText(medico.getEndereco());
            txfNumeroCelular.setText(medico.getNumero_celular());
        }

    }


    private void initComponents(){

        pnlCentro = new JPanel();

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);

        pnlDadosCadastrais = new JPanel();
        pnlDadosCadastrais.setLayout(new BorderLayout());
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlCentro.setLayout(gridBagLayoutDadosCadastrais);

        lblNickname = new JLabel("Nome:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlCentro.add(lblNickname, posicionador);//o add adiciona o rotulo no painel

        txfNickname = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlCentro.add(txfNickname, posicionador);//o add adiciona o rotulo no painel

        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlCentro.add(lblSenha, posicionador);//o add adiciona o rotulo no painel

        txfSenha = new JPasswordField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlCentro.add(txfSenha, posicionador);//o add adiciona o rotulo no painel

        lblCpf = new JLabel("CPF:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 0;
        pnlCentro.add(lblCpf, posicionador);

        txfCpf = new JTextField(14);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfCpf, posicionador);

        lblCep = new JLabel("CEP:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 0;
        pnlCentro.add(lblCep, posicionador);

        txfCep = new JTextField(15);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfCep, posicionador);

        lblComplemento = new JLabel("Complemento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 0;
        pnlCentro.add(lblComplemento, posicionador);

        txfComplemento = new JTextField(20);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfComplemento, posicionador);

        lblDataNascimento = new JLabel("Data de Nascimento:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 0;
        pnlCentro.add(lblDataNascimento, posicionador);

        txfDataNascimento = new JTextField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfDataNascimento, posicionador);

        lblDataCadastro = new JLabel("Data de Cadastro:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 0;
        pnlCentro.add(lblDataCadastro, posicionador);

        txfDataCadastro = new JTextField(10);
        //txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 6;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfDataCadastro, posicionador);

        lblEmail = new JLabel("E-mail:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 0;
        pnlCentro.add(lblEmail, posicionador);

        txfEmail = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 7;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfEmail, posicionador);

        lblEndereco = new JLabel("Endereço:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 0;
        pnlCentro.add(lblEndereco, posicionador);

        txfEndereco = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 8;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfEndereco, posicionador);

        lblNumeroCelular = new JLabel("Número de Celular:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 0;
        pnlCentro.add(lblNumeroCelular, posicionador);

        txfNumeroCelular = new JTextField(40);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 9;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfNumeroCelular, posicionador);

        lblRg = new JLabel("RG:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 0;
        pnlCentro.add(lblRg, posicionador);

        txfRg = new JTextField(15);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 10;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfRg, posicionador);

        lblCrmv = new JLabel("CRMV:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 0;
        pnlCentro.add(lblCrmv, posicionador);

        txfCrmv = new JTextField(15);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 11;
        posicionador.gridx = 1;
        posicionador.anchor = GridBagConstraints.LINE_START;
        pnlCentro.add(txfCrmv, posicionador);

        pnlDadosCadastrais.add(pnlCentro, BorderLayout.CENTER);

        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());

        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade
        btnGravar.setToolTipText("btnGravarMedico"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_medico");

        pnlSul.add(btnGravar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade
        btnCancelar.setToolTipText("btnCancelarMedico"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_medico");

        pnlSul.add(btnCancelar);

        this.add(pnlSul, BorderLayout.SOUTH);

        tbpAbas.add(pnlDadosCadastrais);

        tbpAbas.addTab("Formulário Médico", pnlDadosCadastrais);

        format = new SimpleDateFormat("dd/MM/yyyy");

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {


        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())){

            Medico m = getMedicobyFormulario();//recupera os dados do formulÃ¡rio

            if(m != null){

                try {

                    pnlAMedico.getControle().getConexaoJDBC().persist(m);

                    JOptionPane.showMessageDialog(this, "Medico armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);

                    pnlAMedico.showTela("tela_medico_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar medico! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }


        }else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())){


            pnlAMedico.showTela("tela_medico_listagem");

        }
    }
}