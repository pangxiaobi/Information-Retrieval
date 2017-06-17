import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Test extends HttpServlet {

    ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
          out.println("<!-- TemplateBeginEditable name=\"doctitle\" --><title>APP</title>");
        out.println("<head>");
        out.println("<link href=\"../../css/index.css\" rel=\"stylesheet\" type=\"text/css\" />");
        out.println("<link href=\"../../css/bootstrap.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
        out.println("<script src=\"{% static '../js/jquery-2.1.3.min.js' %}\"></script>");
        out.println("<script src=\"{% static '../js/bootstrap.min.js' %}\"></script>");
        out.println("</head>");
        out.println("<div class=\"container\"><header id=\"site-header\"><div class=\"row\"><div class=\"col-md-4 col-sm-5 col-xs-8\"><div class=\"logo\">");
        out.println("<b></div></div><!-- col-md-4 --><div class=\"col-md-8 col-sm-7 col-xs-4\"><nav class=\"main-nav\" role=\"navigation\"><div class=\"navbar-header\">");
        out.println("<button type=\"button\" id=\"trigger-overlay\" class=\"navbar-toggle\"><span class=\"ion-navicon\"></span></button></div>");
        out.println("<div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\"><ul class=\"nav navbar-nav navbar-right\"><li class=\"cl-effect-11\"><a data-hover=\"Home\" href = \"/index\">relation graph</a></li>");
        out.println("</ul></div><!-- /.navbar-collapse --></nav></div><!-- col-md-8 --></div></header>");
        out.println("	<div class = \"row\"><div class = \"col-md-12\"><div id =\"mainbg\"><img class=\"img-responsive\" src=\"../../img/bg_2.jpg\"/></div>");
        out.println("<div><div id = \"title\">NEWS</div> <div id=\"searchBox\"><form action=\"information_research\" method=\"post\"><input  placeholder =\"search for news\" name = \"name\" id=\"keyword\" type=\"text\" />");
        out.println("<input id=\"search_button\" type=\"submit\" value = \"\" /></form></div></div></div></div></div></html>");
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException
    {
        doGet(request, response);
    }

}
