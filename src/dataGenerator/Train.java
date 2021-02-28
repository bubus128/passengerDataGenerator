package dataGenerator;

import java.util.*;

public class Train {
    private Long id;
    private int statCount;
    private Set<Station> road=new HashSet<>();
    private Set<Date> schedule=new HashSet<>();

    public Train(Long id){
        this.id=id;
        statCount=0;
    }

    public String toString(){
        return (this.id+"\r");
    }

    public void addStation(Station station){
        road.add(station);
        Date date=new Date();
        date.setHours(statCount);
        date.setMinutes(0);
        schedule.add(date);
        statCount++;
    }

    public List<Station> getRoad(){
        return new ArrayList<>(road);
    }

    public boolean stationExistsInRoad(String name){
        return (!road.stream().filter(station -> station.getName()==name).findFirst().isEmpty());
    }

    public long getID() {
        return id;
    }

    public Date getSchedulde(int i){
        return (Date) schedule.toArray()[i];
    }
}
