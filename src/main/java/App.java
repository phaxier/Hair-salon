import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        get("/", (request, response) -> {
         Map<String, Object> model = new HashMap<String, Object>();
         model.put("stylist", Stylist.all());
         model.put("template", "templates/index.vtl");
         return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/:stylist_id/clients/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":stylist_id")));
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            model.put("Stylist", stylist);
            model.put("client", client);
            model.put("template", "templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());




    }

    }