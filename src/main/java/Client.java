import java.time.LocalDateTime;

public class Client {

    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private int id;
    private int categoryId;

    public Client(String description, int categoryId) {
        this.description = description;
        completed = false;
        createdAt = LocalDateTime.now();
        this.categoryId = categoryId;

    }
    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId(){ return categoryId;}
}
