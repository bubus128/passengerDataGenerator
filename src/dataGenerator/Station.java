package dataGenerator;

public class Station {
    private String name;


    public Station(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return(this.name)+"\r";
    }
}
