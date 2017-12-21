import java.util.ArrayList;

public class Record {

    ArrayList<String> members = new ArrayList<>();
    String day;
    String time;

    public void addMember(String name){
        members.add(name);
    }

    public void removeMember(String name) {
        members.remove(name);
    }

}
