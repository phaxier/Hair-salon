import org.sql2o.Connection;

import java.time.LocalDateTime;
import java.util.List;

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

    public int getCategoryId() {
        return categoryId;
    }


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


        public static List<Client> all() {
            String sql = "SELECT id, description, categoryId FROM tasks";
            try (Connection con = DB.sql2o.open()) {
                return con.createQuery(sql).executeAndFetch(Client.class);
            }
        }

        @Override
        public boolean equals(Object otherClient) {
            if (!(otherClient instanceof Client)) {
                return false;
            } else {
                Client newClient = (Client) otherClient;
                return this.getDescription().equals(newClient.getDescription()) &&
                        this.getId() == newClient.getId() &&
                        this.getCategoryId() == newClient.getCategoryId();

            }
        }
    }

