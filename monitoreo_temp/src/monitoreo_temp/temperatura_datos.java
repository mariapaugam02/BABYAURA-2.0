/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo_temp;

// Esta clase nos sirve como contenedor
// para saber que datos son los que vamos
//a enviar a flutter a visualizar.

public class temperatura_datos {
    
    public double valor;
    public String fecha;

    public temperatura_datos(double valor) {
        this.valor = valor;
        
    }
    
       public double getValor() {
        return valor;
    }

   
}
