package dataGenerator;

import java.util.Date;

public class Infected {
    private Person person;
    private Date dateOfDetection;
    private Date dateOfInfection;

    public Infected(Person person,Date dateOfDetection,int days){
        this.person=person;
        this.dateOfDetection=new Date(dateOfDetection.getTime()+(1000 * 60 * 60 * 24));
        dateOfInfection=new Date(dateOfDetection.getTime()-(1000 * 60 * 60 * 24)*days);
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
        return(person.getPesel()+";"+person.getName()+";"+person.getSurename()+";"+timeString(dateOfDetection)+";"+timeString(dateOfInfection)+"\r");
    }
}
