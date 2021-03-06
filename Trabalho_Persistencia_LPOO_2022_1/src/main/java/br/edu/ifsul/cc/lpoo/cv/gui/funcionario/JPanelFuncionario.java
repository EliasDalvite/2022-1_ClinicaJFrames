
package br.edu.ifsul.cc.lpoo.cv.gui.funcionario;

import br.edu.ifsul.cc.lpoo.cv.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

public class JPanelFuncionario extends JPanel{

    private CardLayout cardLayout;
    private final Controle controle;

    public JPanelFuncionario(Controle controle){

        this.controle = controle;
        initComponents();
    }

    private void initComponents(){

        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

    }

    public void showTela(String nomeTela){

        cardLayout.show(this, nomeTela);
    }

    /**
     * @return the controle
     */
    public Controle getControle(){
        return controle;
    }
}