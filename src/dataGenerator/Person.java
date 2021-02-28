package dataGenerator;

public class Person {
    private String name;
    private String surename;
    private Long pesel;
    private int postalNumber;

    Person(String name, String surename,Long pesel,int postalNumber){
        this.name=name;
        this.surename=surename;
        this.pesel=pesel;
        this.postalNumber=postalNumber;
    }
    public String getName(){
        return name;
    }
    public String getSurename(){
        return surename;
    }
    public String toString(){
        return(this.pesel+"|"+this.name+"|"+this.surename+"|"+postalNumber%100+'-'+postalNumber/100+'\r');
    }
    public Long getPesel() {
        return pesel;
    }


}
