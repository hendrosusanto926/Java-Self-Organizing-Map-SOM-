package tupro2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tupro2 {

    static double euclidan(double[] objek, double[] neuron){
        return Math.sqrt( 
                   Math.pow( ((objek[0])-neuron[0]),2)+
                   Math.pow( ((objek[1])-neuron[1]),2));
    }
    static double Tx(double[] neuronP, double[] neuron, double Q){
        return Math.exp(-(euclidan(neuronP,neuron))/(2*(Math.pow(Q, 2))));
    }
    static double deltaW(double nt,double[] neuronP, double[] neuron, double Q, double[] x, int idx){
        return nt*Tx(neuronP, neuron, Q)*(x[idx]-neuron[idx]);
    }
    static int neuronterdekat(double[] objek, double[][] neuron){
        int a=0;
        double b=1000;
        for (int i = 0; i < neuron.length; i++) {
           if(b>euclidan(objek,neuron[i])){
               b=euclidan(objek,neuron[i]);
               a=i;
           }
        }
        return a;
    }
    public static void main(String[] args) throws FileNotFoundException {
        double Q=2; double n=0.1;
        File file= new File("Tugas 2 ML Genap 2018-2019 Dataset Tanpa Label.xls");
        Scanner scan= new Scanner(file);
        double[][] neuron = new double [11][2];
        double[][] objek = new double [600][2];
        for (int i = 0; i < neuron.length; i++) {
            neuron[i][0]= (Math.random()*20);
            neuron[i][1]= (Math.random()*20);
        } 
        for (int i = 0; i < objek.length; i++) {
            String[] c = scan.nextLine().split(",");
            objek[i][0] = Double.parseDouble(c[0]);
            objek[i][1] = Double.parseDouble(c[1]);
        }
        int j=0;
        while (j<100){
            for (int i = 0; i < objek.length; i++) {
                int a=neuronterdekat(objek[i],neuron);
                if (a==0){
                    double d=deltaW(n,neuron[a], neuron[a+1],Q, objek[i], 0);
                    double e=deltaW(n,neuron[a], neuron[a+1],Q, objek[i], 1);
                    double f=deltaW(n,neuron[a], neuron[a],Q, objek[i], 0);
                    double g=deltaW(n,neuron[a], neuron[a],Q, objek[i], 1);
                    neuron[a][0]=neuron[a][0]+f;
                    neuron[a][1]=neuron[a][1]+g;
                    neuron[a+1][0]=neuron[a+1][0]+d;
                    neuron[a+1][0]=neuron[a+1][1]+e;
                }
                else if (a==neuron.length-1){
                    double d=deltaW(n,neuron[a], neuron[a-1],Q, objek[i], 0);
                    double e=deltaW(n,neuron[a], neuron[a-1],Q, objek[i], 1);
                    double f=deltaW(n,neuron[a], neuron[a],Q, objek[i], 0);
                    double g=deltaW(n,neuron[a], neuron[a],Q, objek[i], 1);
                    neuron[a][0]=neuron[a][0]+f;
                    neuron[a][1]=neuron[a][1]+g;
                    neuron[a-1][0]=neuron[a-1][0]+d;
                    neuron[a-1][0]=neuron[a-1][1]+e;
                }
                else{
                    double d=deltaW(n,neuron[a], neuron[a+1],Q, objek[i], 0);
                    double e=deltaW(n,neuron[a], neuron[a+1],Q, objek[i], 1);
                    double f=deltaW(n,neuron[a], neuron[a],Q, objek[i], 0);
                    double g=deltaW(n,neuron[a], neuron[a],Q, objek[i], 1);
                    double h=deltaW(n,neuron[a], neuron[a-1],Q, objek[i], 0);
                    double k=deltaW(n,neuron[a], neuron[a-1],Q, objek[i], 1);
                    neuron[a][0]=neuron[a][0]+f;
                    neuron[a][1]=neuron[a][1]+g;
                    neuron[a+1][0]=neuron[a+1][0]+d;
                    neuron[a+1][0]=neuron[a+1][1]+e;
                    neuron[a-1][0]=neuron[a-1][0]+h;
                    neuron[a-1][0]=neuron[a-1][1]+k;
                }
            }
            n = n*Math.exp(-j/2);
            Q = Q*Math.exp(-j/2);
            if (n<0.000001){
                n = 0.1; Q = 2;
            }
            j++;
        } double sse=0;
        for (int i = 0; i < objek.length; i++) {
            int a = neuronterdekat(objek[i],neuron);
            sse=sse+euclidan(objek[i],neuron[a]);
        }
        for (int i = 0; i < objek.length; i++) {
            System.out.println((i+1)+".  "+objek[i][0]+"  "+objek[i][1]+"  "+neuronterdekat(objek[i],neuron));
        }System.out.println("Nilai SSE : "+sse);
    }    
}
