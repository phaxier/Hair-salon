import org.sql2o.Connection;

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


    public void save() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO tasks (description, categoryId) VALUES (:description, :categoryId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("description", this.description)
                    .addParameter("categoryId", this.categoryId)
                    .executeUpdate()
                    .getKey();
        }
    }
}
