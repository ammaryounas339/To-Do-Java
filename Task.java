
class Task {
    private String description;
    private Priority priority;
    public Task(String description, Priority priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Priority getPriority() {
        return priority;
    }
   
}
enum Priority {
    HIGH,
    MEDIUM,
    LOW
}
