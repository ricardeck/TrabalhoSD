package br.edu.ifba.ads.produtorconsumidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ads
 */
import java.util.ArrayList;

public class Programa {
    
        int itemCount;
        ArrayList buffer;
        Semaphore mutex;
        Semaphore items;
    
        public Programa () {
            itemCount = 0;
            buffer = new ArrayList(); 
            mutex = new Semaphore(1);
            items = new Semaphore(0);
        }
        
        Programa (int itemCount, ArrayList buffer, Semaphore mutex, Semaphore items) {
            itemCount = 0;
            buffer = new ArrayList(); 
            mutex = new Semaphore(1);
            items = new Semaphore(0);
        }
    
        int compartilhada;
    
        public static void main(String[] args) {
               Programa t = new Programa();
               t.run();
        } 
        
        public void run() {
            Consumer c = new Consumer(this);
            Producer p = new Producer(this);
            c.start();
            p.start();
            Consumer c1 = new Consumer(this);
            Producer p1 = new Producer(this);
            c1.start();
            p1.start();
        }

		public int getItemCount() {
			return itemCount;
		}
    
}
