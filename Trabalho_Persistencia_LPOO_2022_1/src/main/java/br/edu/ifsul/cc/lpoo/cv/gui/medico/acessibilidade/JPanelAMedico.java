
package br.edu.ifsul.cc.lpoo.cv.gui.medico.acessibilidade;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;


public class JPanelAMedico extends JPanel {

    private CardLayout cardLayout;
    private Controle controle;

    private JPanelAMedicoFormulario formulario;
    private JPanelAMedicoListagem listagem;

    public JPanelAMedico(Controle controle){

        this.controle = controle;
        initComponents();
    }

    private void initComponents(){

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        formulario = new JPanelAMedicoFormulario(this, controle);
        listagem = new JPanelAMedicoListagem(this, controle);

        this.add(formulario, "tela_medico_formulario");
        this.add(listagem, "tela_medico_listagem");

        cardLayout.show(this, "tela_medico_listagem");
    }

    public void showTela(String nomeTela){

        if(nomeTela.equals("tela_medico_listagem")){

            listagem.populaTable();

        }else if(nomeTela.equals("tela_medico_formulario")){

           // getFormulario().populaComboCargo();
        }

        cardLayout.show(this, nomeTela);

    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }

    /**
     * @return the formulario
     */
    public JPanelAMedicoFormulario getFormulario() {
        return formulario;
    }
}