package dataGenerator;

import java.util.Date;

public class Stop {
    private Long id;
    private Station station;
    private Stop nextStop;
    private Date arrivalTime;
    private Date departureTime;
    private Date realArrivalTime;
    private Date realDepartureTime;

    public Stop(Long id, Station station,Date day){
        this.id=id;
        this.station=station;
        this.arrivalTime=new Date(day.getTime());
        this.departureTime=new Date(day.getTime());
    }
    private String doubleFormat(int a){
        if (a<10)
            return("0"+a);
        else
            return String.valueOf(a);
    }

    private String timeString(Date date){
        int year=date.getYear()+1900;
        String month=doubleFormat(date.getMonth()+1);
        String day=doubleFormat(date.getDate());
        String hours=doubleFormat(date.getHours());
        String minutes=doubleFormat(date.getMinutes());
        String secounds=doubleFormat(date.getSeconds());
        return(year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+secounds+".0000");
    }

    public String toString(){
        Long nextStopID;
        if(nextStop!=null)
            return(id+"|"+station.getName()+"|"+timeString(arrivalTime)+"|"+timeString(departureTime)+"|"+timeString(realArrivalTime)+"|"+timeString(realDepartureTime)+"|"+ nextStop.getID()+"\r");
        else
            return(id+"|"+station.getName()+"|"+timeString(arrivalTime)+"|"+timeString(departureTime)+"|"+timeString(realArrivalTime)+"|"+timeString(realDepartureTime)+"|"+ "null"+"\r");
    }
    public void setTime(int i){
        arrivalTime.setHours(i);
        arrivalTime.setMinutes(0);
        departureTime.setHours(i);
        departureTime.setMinutes(10);
    }

    public int getNumber(){
        return arrivalTime.getHours();
    }

    public void setDelay(int aDelay,int dDelay){
        Date aDate=new Date(arrivalTime.getTime());
        Date dDate=new Date(departureTime.getTime());
        aDelay+=arrivalTime.getMinutes();
        dDelay+=departureTime.getMinutes()+aDelay;
        aDate.setHours(arrivalTime.getHours());
        aDate.setMinutes(aDelay);
        dDate.setHours(departureTime.getHours());
        dDate.setMinutes(dDelay);
        this.realArrivalTime=aDate;
        this.realDepartureTime=dDate;
    }

    public Long getID(){
        return id;
    }

    public void setTime(Date arrivalTime, Date departureTime){
        this.arrivalTime=arrivalTime;
        this.departureTime=departureTime;
    }

    public void setNextStop(Stop stop){
        nextStop=stop;
    }

    public Stop getNextStop(){
        return nextStop;
    }
}
