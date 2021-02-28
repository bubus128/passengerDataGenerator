package dataGenerator;

import java.util.Random;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.scalb;

public class Car {
    private Long id;
    private Train train;
    private int number;
    private int countSeat;
    private int countDisabled;
    private int clas;

    public Car(Long id,Train train,int number){
        this.id=id;
        this.train=train;
        this.number=number;
        Random rand=new Random();
        clas=abs(rand.nextInt()%2)+1;
        countSeat=20;
        countDisabled=10;
    }

    public String toString(){
        return(this.id+"|"+this.train.getID()+"|"+this.number+"|"+this.countDisabled+"|"+this.countSeat+"|"+this.clas)+"\r";
    }
    public Train getTrain(){
        return train;
    }

    public Long getID(){
        return id;
    }
}
