package dataGenerator;

import java.util.Date;

public class Passage {
    private Long id;
    private Stop firstStop;
    private Train train;
    private int countStops;

    public Passage(Long id, Train train){
        this.id=id;
        this.train=train;
        countStops=0;
    }

    public String toString(){
        return(this.id+"|"+this.firstStop.getID()+"|"+this.train.getID()+"|"+this.countStops)+"\r";
    }

    public Long getID(){
        return id;
    }

    public void addStop(Stop stop){
        if(firstStop==null){
            firstStop=stop;
            return;
        }
        Stop nextStop=firstStop;
        while(nextStop.getNextStop()!=null){
            nextStop=nextStop.getNextStop();
        }
        nextStop.setNextStop(stop);
        countStops++;
    }

    public Stop getFirstStop(){
        return firstStop;
    }

    public Stop getLastStop(){
        Stop lastStop=firstStop;
        if(countStops==0){
            return null;
        }
        for(int i=0;i<countStops-1;i++){
            lastStop=lastStop.getNextStop();
        }
        return lastStop;
    }
}
