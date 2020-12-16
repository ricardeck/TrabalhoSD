package br.edu.ifba.ads.produtorconsumidor;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ads
 */
public class Consumer extends Thread {

	Programa a;

	public Consumer(Programa x) {

		a = x;
	}

	public void run() {

		try {
//                   while (true) {
//                       a.items.down();
//                       a.mutex.down();
//                       int item;
//                       item = (Integer) a.buffer.get(0);
//                       a.buffer.remove(0);
//                       a.itemCount--;
//                       System.out.println("consumer: consuming item "+item);
//                       a.mutex.up();
//                       for (int i =0;i<10000;i++);
//                   }
			if (a.itemCount > 0) {
				a.items.down();
				a.mutex.down();
				int item;
				item = (Integer) a.buffer.get(0);
				a.buffer.remove(0);
				a.itemCount--;
				a.mutex.up();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
