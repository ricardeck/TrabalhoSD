package br.edu.ifba.ads.produtorconsumidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ads
 */
public class Producer extends Thread {
    
        Programa a;

        int contador;
        public Producer(Programa x) {

               a = x;
               contador =x.getItemCount();
        }

        public void run() {

//                   while (true) {
//                       a.mutex.down();
//                       a.items.up();
//                       contador ++;
//                       a.buffer.add(contador);
//                       a.itemCount++;
//                       System.out.println("produtor: producing item "+contador);
//                       a.mutex.up();
//                       for (int i =0;i<10000;i++);
//                   }
                       a.mutex.down();
                       a.items.up();
                       contador ++;
                       a.buffer.add(contador);
                       a.itemCount++;
//                       System.out.println("produtor: producing item "+contador);
                       a.mutex.up();
        }
    
    
}
