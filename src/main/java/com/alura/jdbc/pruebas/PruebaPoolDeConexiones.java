package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import java.sql.Connection;

/**
 *
 * @author scris
 */
public class PruebaPoolDeConexiones {

    public static void main( String[] args ) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        for (int i = 0; i < 20; i++) {
            Connection con = connectionFactory.recuperaConexion();
            System.out.println("Abriendo Con " + ( i + 1 ));
        }
        System.out.println("Fin");

    }
}
