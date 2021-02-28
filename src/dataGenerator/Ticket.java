package dataGenerator;

public class Ticket {
    private Long id;
    private Person person;
    private Passage passage;
    private Stop firstStop;
    private Stop lastStop;
    private int seat;
    private int car;
    private boolean disabled;

    public Ticket(Long id, Person person, Passage passage, Stop firstStop,Stop lastStop,int seat,int car,boolean disabled){
        this.id=id;
        this.person=person;
        this.passage=passage;
        this.firstStop=firstStop;
        this.lastStop=lastStop;
        this.seat=seat;
        this.car=car;
        this.disabled=disabled;
    }
    public Long getID(){
        return id;
    }

    public String toString(){
        return(this.id+"|"+this.person.getPesel()+"|"+this.passage.getID()+"|"+ this.firstStop.getID()+"|"+this.lastStop.getID()+"|"+this.seat+"|"+this.car+"|"+this.disabled+'\r');
    }
}
