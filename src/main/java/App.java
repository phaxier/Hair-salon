import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());

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

        post("/stylists/:stylist_id/clients/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Client client = Client.find(Integer.parseInt(request.params("id")));
            String description = request.queryParams("description");
            Stylist stylist = Stylist.find(client.getStylistId());
            client.update(description);
            String url = String.format("/stylists/%d/client/%d", stylist.getId(), client.getId());
            response.redirect(url);
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/clients", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
            String description = request.queryParams("description");
            Client newClient = new Client(description, stylist.getId());
            newClient.save();
            model.put("stylist", stylist);
            model.put("template", "templates/stylist-client-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/clients/:id", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            model.put("client",client);
            model.put("template","templates/client.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        get("/stylists/new", (request, response)->{
            Map<String, Object> model =new HashMap<String, Object>();
            model.put("template","templates/stylist-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/stylists/:id/clients/newClient", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));

            model.put("stylist", stylist);
            model.put("template", "templates/stylist-clients-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

            post("/stylists", (request,response)->{
                Map<String, Object> model = new HashMap<String, Object>();
                String name = request.queryParams("name");
                Stylist newStylist = new Stylist (name);
                newStylist.save();
                model.put("template", "templates/stylist-success.vtl");
                return new ModelAndView(model, layout);
                }, new VelocityTemplateEngine());

            get("/clients", (request, response) -> {
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("clients", Client.all());
                model.put("template","templates/clients.vtl");
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

            get ("/stylists", (request, response)->{
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("stylists", Stylist.all());
                model.put("template", "templates/stylists.vtl");
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());


            get("/stylists/:id", (request, response)->{
                Map<String, Object> model = new HashMap<String, Object>();
                Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
                model.put("stylist", stylist);
                model.put("template", "templates/stylist.vtl");
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());


            post("/stylists/:stylist_id/clients/:id/delete", (request, response)->{
                HashMap<String, Object> model = new HashMap<String, Object>();
                Client client = Client.find(Integer.parseInt(request.params("id")));
                Stylist stylist = Stylist.find(client.getStylistId());
                client.delete();

                model.put("stylist", stylist);
                model.put("template", "templates/stylist.vtl");
                return new ModelAndView(model, layout);
            }, new VelocityTemplateEngine());

    }



    }