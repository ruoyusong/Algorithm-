package algs4;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Random;
import java.util.Scanner;

public class percolation {
    private WeightedQuickUnionUF uf;
   private int sites[][];

public percolation(int n){
        sites=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                sites[i][j]=0;
            }
        }
        uf=new WeightedQuickUnionUF(n*n);
   // System.out.print("this is percolation\n");

}
public boolean conn(int p,int q){
   return uf.find(p)==uf.find(q);
}
public void open(int i,int j,int n){
         sites[i][j]=1;
         if(j>0 && sites[i][j-1]>0)      uf.union(i*n+j,i*n+j-1);
         if(j<(n-1) && sites[i][j+1]>0)   uf.union(i*n+j,i*n+j+1);
         if(i>0 && sites[i-1][j]>0)       uf.union((i-1)*n+j,i*n+j);
         if(i<(n-1) && sites[i+1][j]>0)    uf.union((i+1)*n+j,i*n+j);
 }
public boolean isopen(int i,int j){
    if(sites[i][j]>0)return true;
    else return false;
}
public  boolean isfull(int i,int j,int n){
        if(i==0) return true;
        else{
            for(int m=0; m<n; m++){

                if(conn(m,i*n+j)==true) return  true;
            }
        }
        return false;
}
public boolean ispercolate(int n){
        for(int i=0;i<n;i++){
            if(isfull(n-1,i,n)) return true;
        }
       return false;
}
public void show(int n){
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            System.out.print(sites[i][j]+" ");
        }
        System.out.println();
    }
}


public static void main(String args[]){
    long startTime = System.currentTimeMillis(); //获取开始时间
    System.out.print("请输入网格边长、循环次数：");
    Scanner sc=new Scanner(System.in);
    int n=sc.nextInt();
    int t=sc.nextInt();
    int x[]=new int[t];
    int m=0;
    while(m<t){
        x[m]=0;
        percolation square=new percolation(n*n);
        for(int w=0;w<n*n;w++){
            x[m]++;
            int i,j;
            while(true){
                Random rand=new Random();
                int p=rand.nextInt(n*n);
                 i=p/n;
                 j=p%n;
                if(square.isopen(i,j)==false) break;
            }
            square.open(i,j,n);
            if(square.ispercolate(n))  break;
        }
        m++;
    }
    double y[]=new double [t];
    for(int i=0;i<t;i++) y[i]=(double)x[i]/(n*n);
    double all1=0,all2=0;
    for(int i=0;i<t;i++)   all1=all1+y[i];
    double u=all1/t;

    for(int i=0;i<t;i++) all2=all2+Math.pow(y[i]-u,2);
    double v=all2/(t-1);   //2
    double temp1=u-1.96*Math.sqrt(v)/Math.sqrt(t);
    double temp2=u+1.96*Math.sqrt(v)/Math.sqrt(t);
    long endTime = System.currentTimeMillis(); //获取结束时间
    System.out.print("阈值是："+u+"\n");
    System.out.println("标准差是："+Math.sqrt(v));
    System.out.println("置信区间是:["+temp1+","+temp2+"]");
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); 
}
}
