package com.mygdx.game.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Juego;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.sprites.Arquero;
import com.mygdx.game.utiles.Render;

public class HiloCliente extends Thread {

    private DatagramSocket conexion;
    private InetAddress ipServer;
    private int puerto = 38888;

    public boolean fin = false;
    public static int idCliente = -1;


    public HiloCliente() {
        try {
            conexion = new DatagramSocket();
            // Envia mensaje a todas las IPS
            ipServer = InetAddress.getByName("255.255.255.255");
            System.out.println("Inicia cliente");
            enviaerMensaje("Conexion");
        } catch (SocketException | UnknownHostException e) {
            fin = true;
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        do {
            byte[] datos = new byte[1024];
            DatagramPacket dp = new DatagramPacket(datos, datos.length);
            try {
                conexion.receive(dp);
                procesarMensaje(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!fin);
        conexion.close();
    }

    public void enviaerMensaje(String msg) {
        System.out.println("enviar " + msg);
        byte[] data = msg.getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, ipServer, puerto);
        try {
            conexion.send(dp);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void procesarMensaje(DatagramPacket dp) {
        String msg = (new String(dp.getData())).trim();
        String[] msgCompuesto = msg.split("#");
        System.out.println("Mensaje " + msg);
        	if (msgCompuesto[0].equals("OK")) {
        		idCliente = Integer.parseInt(msgCompuesto[1]);
        		ipServer = dp.getAddress();
        	} else if (msg.equals("Empieza")) {
        		System.out.println("Llega EMPIEZA");
        		Juego.empieza = true;
        	} else if (msgCompuesto[0].equals("movimiento")) {
        		Integer idJugador = Integer.parseInt(msgCompuesto[1]);
        		Float x = Float.parseFloat(msgCompuesto[2]);
        		Float y = Float.parseFloat(msgCompuesto[3]);
        		if (idJugador == 0) {
        			PantallaNivelUno.player1Position = new Vector2(x, y);
        		} else
        			PantallaNivelUno.player2Position = new Vector2(x, y);
        	} else if (msgCompuesto[0].equals("enemigo_mover")) {
        		Integer idEnemigo = Integer.parseInt(msgCompuesto[1]);
        		Float x = Float.parseFloat(msgCompuesto[2]);
        		Float y = Float.parseFloat(msgCompuesto[3]);
        		if (PantallaNivelUno.creador != null && PantallaNivelUno.creador.getArquero().size > 0) {
                	Arquero enemigo = PantallaNivelUno.creador.getArquero().get(idEnemigo);
                	if (enemigo == null) return;
                	enemigo.proximoX = x;
        		}
        		} else if (msgCompuesto[0].equals("enemigo_morir")) {
        			Integer idEnemigo = Integer.parseInt(msgCompuesto[1]);
        			if (PantallaNivelUno.creador != null && PantallaNivelUno.creador.getArquero().size > 0) {
        					Arquero enemigo = PantallaNivelUno.creador.getArquero().get(idEnemigo);
        						if (enemigo == null) return;
        							enemigo.muerto = true;
        			}
        				} else if (msgCompuesto[0].equals("ninja_muerto")) {
        					Integer idJugador = Integer.parseInt(msgCompuesto[1]);
        						if (idJugador == 0) {
        							PantallaNivelUno.player1.ninjaEstaMuerto = true;
        						} else PantallaNivelUno.player2.ninjaEstaMuerto = true;
        				} else if (msgCompuesto[0].equals("termina")) {
        					fin = true;
        				}
    		}
	}
