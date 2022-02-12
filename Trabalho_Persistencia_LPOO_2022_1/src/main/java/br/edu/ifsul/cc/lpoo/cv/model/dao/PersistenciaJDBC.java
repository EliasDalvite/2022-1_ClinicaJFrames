
package br.edu.ifsul.cc.lpoo.cv.model.dao;

import br.edu.ifsul.cc.lpoo.cv.model.Agenda;
import br.edu.ifsul.cc.lpoo.cv.model.Medico;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        }else if(c == Medico.class){

            PreparedStatement ps = this.con.prepareStatement("select numero_crmv, cpf from tb_medico where cpf = ?");
            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Medico m = new Medico();
                m.setNumero_crmv(rs.getString("numero_crmv"));
                m.setCpf(rs.getString("cpf"));
                
                ps.close();

                return m;
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


        }else if (o instanceof Medico){

            Medico med = (Medico) o; //converter o para o e que é do tipo Endereco

            //descobrir se é para realiar insert ou update.
            if (med.getData_cadastro() == null) {
                
                PreparedStatement ps = this.con.prepareStatement("insert into tb_pessoa"
                        + " (cpf, rg, nome, senha, numero_celular, email, cep, endereco, complemento, data_cadastro, data_nascimento, tipo)"
                        + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, 'M') ");
                ps.setString(1, med.getCpf());
                ps.setString(2, med.getRg());
                ps.setString(3, med.getNome());
                ps.setString(4, med.getSenha());
                ps.setString(5, med.getNumero_celular());
                ps.setString(6, med.getEmail());
                ps.setString(7, med.getCep());
                ps.setString(8, med.getEndereco());
                ps.setString(9, med.getComplemento());
                ps.setDate(10, new java.sql.Date(med.getData_nascimento().getTimeInMillis()));

                ps.execute();
            
                PreparedStatement ps2 = this.con.prepareStatement("insert into tb_medico (numero_crmv, cpf) values (?, ?);");
                
                ps2.setString(1, med.getNumero_crmv());
                ps2.setString(2, med.getCpf());
               
                ps2.execute();

            }else{
                //update.
                
                PreparedStatement ps = this.con.prepareStatement("update tb_pessoa set"
                        + " rg = ?, nome = ?, senha = ?, numero_celular = ?, email = ?, cep = ?,"
                        + " endereco = ?, complemento = ?, now(), data_nascimento = ?, tipo = 'M' where cpf = ?");
                
                ps.setString(1, med.getRg());
                ps.setString(2, med.getNome());
                ps.setString(3, med.getSenha());
                ps.setString(4, med.getNumero_celular());
                ps.setString(5, med.getEmail());
                ps.setString(6, med.getCep());
                ps.setString(7, med.getEndereco());
                ps.setString(8, med.getComplemento());
                ps.setDate(9, new java.sql.Date(med.getData_nascimento().getTimeInMillis()));
                ps.setString(10, med.getCpf());

                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("update tb_medico set numero_crmv = ? where cpf = ?");
                
                ps2.setString(1, med.getNumero_crmv());
                ps2.setString(2, med.getCpf());
                
                ps2.execute();//executa o comando.
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
        
        //PreparedStatement ps = this.con.prepareStatement("select cpf, rg, nome, senha, numero_celular, email, data_cadastro,"
          //      + "data_nascimento, cep, endereco, complemento, numero_crmv from tb_pessoa order by id asc");

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
    
}