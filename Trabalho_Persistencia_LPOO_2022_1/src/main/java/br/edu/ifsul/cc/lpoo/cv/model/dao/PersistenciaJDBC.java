
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PersistenciaJDBC implements InterfacePersistencia{
   
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/clinica_lpoo_2021_2";
    private Connection con = null;

    public PersistenciaJDBC () throws Exception {

        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");

        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);
    
    }


    @Override
    public Boolean conexaoAberta() {

        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    @Override
    public void fecharConexao() {

        try{
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){
            e.printStackTrace();//gera uma pilha de erro na saida.
        }

    }
    
    @Override
    public Object find(Class c, Object id) throws Exception {

        if(c == Agenda.class){

            //tb_agenda
            PreparedStatement ps = this.con.prepareStatement("select id, data_inicio, data_fim, observacao from tb_agenda where id = ?");
            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                Agenda a = new Agenda();
                
                a.setId(rs.getInt("id"));        
                a.setObservacao(rs.getString("observacao"));
                
                Calendar data_inicio = Calendar.getInstance();
                Calendar data_fim = Calendar.getInstance();
                
                data_inicio.setTimeInMillis(rs.getDate("data_inicio").getTime());
                a.setData_inicio(data_inicio);
                
                data_fim.setTimeInMillis(rs.getDate("data_fim").getTime());
                a.setData_fim(data_fim);
                
                ps.close();

                return a;
            }

        }else if (c == Medico.class) {

            PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                    + " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, m.numero_crmv"
                    + " FROM tb_pessoa p INNER JOIN tb_medico m ON p.cpf = m.cpf WHERE p.cpf = ?;");

            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medico m = new Medico();

                m.setCpf(rs.getString("cpf"));
                m.setRg(rs.getString("rg"));
                m.setNome(rs.getString("nome"));
                m.setSenha(rs.getString("senha"));
                m.setNumero_celular(rs.getString("numero_celular"));
                m.setEmail(rs.getString("email"));
                m.setEndereco(rs.getString("endereco"));
                m.setCep(rs.getString("cep"));
                m.setComplemento(rs.getString("complemento"));
                m.setNumero_crmv(rs.getString("numero_crmv"));

                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                m.setData_nascimento(dtNascimento);

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                m.setData_cadastro(dtCadastro);

                ps.close();
                return m;
            }

        }else if (c == Funcionario.class) {

            PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                    + " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, f.cargo, f.numero_ctps, f.numero_pis"
                    + " FROM tb_pessoa p INNER JOIN tb_funcionario f ON p.cpf = f.cpf WHERE p.cpf = ?;");

            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Funcionario f = new Funcionario();

                f.setCpf(rs.getString("cpf"));
                f.setRg(rs.getString("rg"));
                f.setNome(rs.getString("nome"));
                f.setSenha(rs.getString("senha"));
                f.setNumero_celular(rs.getString("numero_celular"));
                f.setEmail(rs.getString("email"));
                f.setEndereco(rs.getString("endereco"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setCargo(Cargo.valueOf(rs.getString("cargo")));
                f.setNumero_ctps(rs.getString("numero_ctps"));
                f.setNumero_pis(rs.getString("numero_pis"));

                Calendar dtNascimento = Calendar.getInstance();
                dtNascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
                f.setData_nascimento(dtNascimento);

                Calendar dtCadastro = Calendar.getInstance();
                dtCadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                f.setData_cadastro(dtCadastro);

                ps.close();
                return f;
            }
        }
        return null;
    }
        

    @Override
    public void persist(Object o) throws Exception {
        //descobrir a instancia do Object o


        if(o instanceof Agenda){
            Agenda a = (Agenda) o; //converter o para o e que é do tipo Endereco

            //descobrir se é para realiar insert ou update.
            if(a.getId() == null){
                //insert.                                    
                PreparedStatement ps = this.con.prepareStatement("insert into tb_agenda (id, data_inicio, data_fim, observacao, tipoproduto, funcionario_cpf, medico_cpf) values (nextval('seq_agenda_id'), ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

                ps.setDate(1, new java.sql.Date(a.getData_inicio().getTimeInMillis()));
                ps.setDate(2, new java.sql.Date(a.getData_fim().getTimeInMillis()));
                ps.setString(3, a.getObservacao());
                ps.setString(4, a.getTipoproduto().toString());
                ps.setString(5, a.getFuncionario().getCpf());
                ps.setString(6, a.getMedico().getCpf());

                ps.execute();
                
            }else{
                //update.
                PreparedStatement ps = this.con.prepareStatement("update tb_agenda set data_inicio = ?, data_fim = ?, observacao = ?, tipoproduto = ?, funcionario_cpf = ?, medico_cpf = ? where id = ?");

                ps.setDate(1, new java.sql.Date(a.getData_inicio().getTimeInMillis()));
                ps.setDate(2, new java.sql.Date(a.getData_fim().getTimeInMillis()));
                ps.setString(3, a.getObservacao());
                ps.setString(4, a.getTipoproduto().toString());
                ps.setString(5, a.getFuncionario().getCpf());
                ps.setString(6, a.getMedico().getCpf());
                ps.setInt(7, a.getId());


                ps.execute();
            }


        }else if (o instanceof Funcionario) {

            Funcionario f = (Funcionario) o;
            if (f.getData_cadastro() == null) {
                PreparedStatement ps_pessoa = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'F')");

                ps_pessoa.setString(1, f.getCpf());
                ps_pessoa.setString(2, f.getRg());
                ps_pessoa.setString(3, f.getNome());
                ps_pessoa.setString(4, f.getSenha());
                ps_pessoa.setString(5, f.getNumero_celular());
                ps_pessoa.setString(6, f.getEmail());
                ps_pessoa.setString(7, f.getCep());
                ps_pessoa.setString(8, f.getEndereco());
                ps_pessoa.setString(9, f.getComplemento());
                ps_pessoa.setTimestamp(10, new Timestamp(f.getData_nascimento().getTimeInMillis()));

                ps_pessoa.execute();


                PreparedStatement psf = this.con.prepareStatement("insert into tb_funcionario "
                        + "(cargo, numero_ctps, numero_pis, cpf) values (?, ?, ?, ?) ");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());
                psf.execute();


            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, numero_celular = ?, "
                        + "email = ?, cep= ?, endereco = ?, complemento = ?, data_nascimento = ?, tipo = 'F'"
                        + "where cpf = ?");
                ps.setString(1, f.getRg());
                ps.setString(2, f.getNome());
                ps.setString(3, f.getSenha());
                ps.setString(4, f.getNumero_celular());
                ps.setString(5, f.getEmail());
                ps.setString(6, f.getCep());
                ps.setString(7, f.getEndereco());
                ps.setString(8, f.getComplemento());
                ps.setTimestamp(9, new Timestamp(f.getData_nascimento().getTimeInMillis()));
                ps.setString(10, f.getCpf());

                PreparedStatement psf = this.con.prepareStatement("update tb_funcionario set cargo = ?, numero_ctps = ?, numero_pis = ? where cpf = ?");
                psf.setString(1, f.getCargo().toString());
                psf.setString(2, f.getNumero_ctps());
                psf.setString(3, f.getNumero_pis());
                psf.setString(4, f.getCpf());


                ps.execute();
                psf.execute();
            }

            }else if (o instanceof Medico) {
            Medico m = (Medico) o;
            if (m.getData_cadastro() == null) {
                PreparedStatement ps_pessoa = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'M')");

                ps_pessoa.setString(1, m.getCpf());
                ps_pessoa.setString(2, m.getRg());
                ps_pessoa.setString(3, m.getNome());
                ps_pessoa.setString(4, m.getSenha());
                ps_pessoa.setString(5, m.getNumero_celular());
                ps_pessoa.setString(6, m.getEmail());
                ps_pessoa.setString(7, m.getCep());
                ps_pessoa.setString(8, m.getEndereco());
                ps_pessoa.setString(9, m.getComplemento());
                ps_pessoa.setTimestamp(10, new Timestamp(m.getData_nascimento().getTimeInMillis()));

                ps_pessoa.execute();

                PreparedStatement psf = this.con.prepareStatement("insert into tb_medico "
                        + "(numero_crmv, cpf) values (?, ?) ");
                psf.setString(1, m.getNumero_crmv());
                psf.setString(2, m.getCpf());
                psf.execute();
                //System.out.println("O Funcionario com CPF = " + f.getCpf() + " foi cadastrado com sucesso!\n");
            } else {
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set rg = ?, nome = ?, senha = ?, numero_celular = ?, "
                        + "email = ?, cep= ?, endereco = ?, complemento = ?, data_nascimento = ?, tipo = 'M'"
                        + "where cpf = ?");
                ps.setString(1, m.getRg());
                ps.setString(2, m.getNome());
                ps.setString(3, m.getSenha());
                ps.setString(4, m.getNumero_celular());
                ps.setString(5, m.getEmail());
                ps.setString(6, m.getCep());
                ps.setString(7, m.getEndereco());
                ps.setString(8, m.getComplemento());
                ps.setTimestamp(9, new Timestamp(m.getData_nascimento().getTimeInMillis()));
                ps.setString(10, m.getCpf());

                PreparedStatement psf = this.con.prepareStatement("update tb_medico set numero_crmv = ? where cpf = ?");
                psf.setString(1, m.getNumero_crmv());
                psf.setString(2, m.getCpf());

                ps.execute();
                psf.execute();
            }
        }
    }

        @Override
        public void remover(Object o) throws Exception{
        if(o instanceof Agenda){

            Agenda a = (Agenda) o; //converter o para o e que é do tipo Produto

            PreparedStatement ps = this.con.prepareStatement("delete from tb_agenda where id = ? ");// deleta a informação da tabela receita_produto
            ps.setInt(1, a.getId());
            ps.execute();

        }else if(o instanceof Medico){
            Medico med = (Medico) o; //converter o para o e que é do tipo Receita
            
            PreparedStatement ps = this.con.prepareStatement("delete from tb_agenda where medico_cpf = ?");
            ps.setString(1, med.getCpf());
            ps.execute();
            
            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_medico where cpf = ?");// deleta a informação da tabela receita_produto
            ps2.setString(1, med.getCpf());
            ps2.execute();
            
            PreparedStatement ps3 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");// deleta a informação da tabela receita_produto
            ps3.setString(1, med.getCpf());
            ps3.execute();

        }else if(o instanceof Funcionario){

            Funcionario func = (Funcionario) o; //converter o para o e que é do tipo Receita

            PreparedStatement ps = this.con.prepareStatement("delete from tb_agenda where funcionario_cpf = ?");
            ps.setString(1, func.getCpf());
            ps.execute();

            PreparedStatement ps2 = this.con.prepareStatement("delete from tb_funcionario where cpf = ?");// deleta a informação da tabela receita_produto
            ps2.setString(1, func.getCpf());
            ps2.execute();

            PreparedStatement ps3 = this.con.prepareStatement("delete from tb_pessoa where cpf = ?");// deleta a informação da tabela receita_produto
            ps3.setString(1, func.getCpf());
            ps3.execute();
        }
    }
    
    
    @Override
    public List<Agenda> listAgendas() throws Exception {
        
        List<Agenda> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, data_inicio, data_fim, observacao from tb_agenda order by id asc");

        ResultSet rs = ps.executeQuery();//executa a query
       
        lista = new ArrayList();
        while(rs.next()){

            Agenda ag = new Agenda();
            
            ag.setId(rs.getInt("id"));
            
            Calendar data_inicio = Calendar.getInstance();
            Calendar data_fim = Calendar.getInstance();
            
            data_inicio.setTimeInMillis(rs.getDate("data_inicio").getTime());
            ag.setData_inicio(data_inicio);
                
            data_fim.setTimeInMillis(rs.getDate("data_fim").getTime());
            ag.setData_fim(data_fim);
            
            ag.setObservacao(rs.getString("observacao"));
            
            lista.add(ag);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.

        }
        return lista;
        
    }

    @Override
    public List<Medico> listMedicos() throws Exception {

        List<Medico> lista = null;


        PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                +  " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, m.numero_crmv"
                +  " FROM tb_pessoa p INNER JOIN tb_medico m ON p.cpf = m.cpf;");

        ResultSet rs = ps.executeQuery();//executa a query

        lista = new ArrayList();
        while(rs.next()){
            Medico med = new Medico();

            med.setCpf(rs.getString("cpf"));
            med.setRg(rs.getString("rg"));
            med.setNome(rs.getString("nome"));
            med.setSenha(rs.getString("senha"));
            med.setNumero_celular(rs.getString("numero_celular"));
            med.setEmail(rs.getString("email"));
            med.setCep(rs.getString("cep"));
            med.setEndereco(rs.getString("endereco"));
            med.setComplemento(rs.getString("complemento"));
            med.setNumero_crmv(rs.getString("numero_crmv"));


            Calendar data_cadastro = Calendar.getInstance();
            Calendar data_nascimento = Calendar.getInstance();

            data_cadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            med.setData_cadastro(data_cadastro);

            data_nascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            med.setData_nascimento(data_nascimento);

            lista.add(med);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.

        }

        return lista;

    }

    @Override
    public List<Funcionario> listFuncionarios() throws Exception {

        List<Funcionario> lista = null;


        PreparedStatement ps = this.con.prepareStatement("SELECT p.cpf, p.rg, p.nome, p.senha, p.numero_celular,"
                +  " p.email, p.data_cadastro, p.data_nascimento, p.cep, p.endereco, p.complemento, f.numero_ctps, f.numero_pis, f.cargo"
                +  " FROM tb_pessoa p INNER JOIN tb_funcionario f ON p.cpf = f.cpf;");

        ResultSet rs = ps.executeQuery();//executa a query

        lista = new ArrayList();
        while(rs.next()){
            Funcionario func = new Funcionario();

            func.setCpf(rs.getString("cpf"));
            func.setRg(rs.getString("rg"));
            func.setNome(rs.getString("nome"));
            func.setSenha(rs.getString("senha"));
            func.setNumero_celular(rs.getString("numero_celular"));
            func.setEmail(rs.getString("email"));
            func.setCep(rs.getString("cep"));
            func.setEndereco(rs.getString("endereco"));
            func.setComplemento(rs.getString("complemento"));
            func.setNumero_ctps(rs.getString("numero_ctps"));
            func.setNumero_pis(rs.getString("numero_pis"));
            func.setCargo(Cargo.valueOf(rs.getString("cargo")));

            Calendar data_cadastro = Calendar.getInstance();
            Calendar data_nascimento = Calendar.getInstance();

            data_cadastro.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            func.setData_cadastro(data_cadastro);

            data_nascimento.setTimeInMillis(rs.getDate("data_nascimento").getTime());
            func.setData_nascimento(data_nascimento);

            lista.add(func);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.

        }

        return lista;

    }

    @Override
    public Pessoa doLogin(String cpf, String senha) throws Exception {


        Pessoa pessoa = null;

        PreparedStatement ps = this.con.prepareStatement("select p.cpf, p.senha from tb_pessoa p where p.cpf = ? and p.senha = ? ");

        ps.setString(1, cpf);
        ps.setString(2, senha);

        ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1

        if(rs.next()){//se a matriz (ResultSet) tem uma linha

            pessoa = new Pessoa();
            pessoa.setCpf(rs.getString("Cpf"));
        }

        ps.close();
        return pessoa;

    }}