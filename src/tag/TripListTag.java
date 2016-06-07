package tag;

import entity.TripEntity;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * Created by USER on 15.05.2016.
 */
public class TripListTag extends TagSupport {
    private static final long serialVersionUID = 862598879715949214L;

    private Boolean admin;
    private List<TripEntity> trips;

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<table class=\"table\">\n" +
                    "<thead>\n" +
                    "<tr>\n" +
                    "<th>Id</th>\n" +
                    "<th>Заявка</th>\n" +
                    "<th>Водитель</th>\n" +
                    "<th>Выполнена</th>\n");
            if(admin) {
                out.write("<th>Редактирование</th>\n");
            }
            out.write("</tr>\n" +
                    "</thead>\n" +
                    "<tbody>");
            for(TripEntity tripEntity : trips) {
                out.write("<tr>");
                out.write("<td>" + tripEntity.getId() + "</td>");
                out.write("<td>Id: " + tripEntity.getApplicationByApplicationId().getId() +
                        ", груз: " + tripEntity.getApplicationByApplicationId().getCargoWeight() + "</td>");
                out.write("<td>Id: " + tripEntity.getDriverByDriverUserId().getUserId() +
                        ", имя: " + tripEntity.getDriverByDriverUserId().getUserByUserId().getLogin() + "</td>");
                if(tripEntity.getIsComplete()) {
                    out.write("<td><span class=\"glyphicon glyphicon-ok\"></span></td>");
                }
                else {
                    out.write("<td><span class=\"glyphicon glyphicon-remove\"></span></td>");
                }
                if(admin) {
                    out.write("<td><button type=\"submit\" class=\"btn btn-default\" " +
                            "data-toggle=\"modal\" data-target=\"#modal" + tripEntity.getId() +
                            "\">Изменить состояние</button></td>");
                    out.write("<div id=\"modal" + tripEntity.getId() + "\" class=\"modal fade\" role=\"dialog\">");
                    out.write("<div class=\"modal-dialog modal-lg\">\n" +
                            "<div class=\"modal-content\">\n" +
                            "<form action=\"motor_depot\" method=\"post\">\n" +
                            "<div class=\"modal-header\">\n" +
                            "<button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\n" +
                            "<h4 class=\"modal-title\">Изменить состояние рейса</h4>\n" +
                            "</div>\n" +
                            "<div class=\"modal-body\">");
                    out.write("<p>Рейс номер " + tripEntity.getId() + ", автомобиль номер " +
                            tripEntity.getDriverByDriverUserId().getTruckByTruckId().getId() + ", водитель: " +
                    tripEntity.getDriverByDriverUserId().getUserByUserId().getLogin() + "</p>");
                    out.write("<p><div class=\"form-group\">\n" +
                            "<label for=\"stateList\">Состояние рейса</label>\n" +
                            "<select class=\"form-control\" id=\"stateList\" name=\"chosenState\">\n" +
                            "    <option value=\"true\">Выполнен</option>\n" +
                            "    <option value=\"false\">Не выполнен</option>\n" +
                            "</select>\n" +
                            "</div></p></div>");
                    out.write("<div class=\"modal-footer\">\n" +
                            "<button type=\"submit\" class=\"btn btn-primary\">Сохранить</button>\n" +
                            "<input type=\"hidden\" name=\"command\" value=\"change_trip_state\"/>\n" +
                            "<input type=\"hidden\" name=\"tripId\" value=\"" +
                            tripEntity.getId() + "\"/>\n" +
                            "</div>");
                    out.write("</form></div></div></div>");
                }
                out.write("</tr>");
            }
            out.write("</tbody></table>");
        } catch (IOException ex) {
            throw new JspTagException(ex.getMessage());
        }
        return SKIP_BODY;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<TripEntity> getTrips() {
        return trips;
    }

    public void setTrips(List<TripEntity> trips) {
        this.trips = trips;
    }
}
