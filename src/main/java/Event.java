public class Event extends Task {
    private String at;

    public Event(String name, String at) {
        super(name);
        this.at = at;
    }

    public String getAt() {
        return this.at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + this.at + ")";
    }
}