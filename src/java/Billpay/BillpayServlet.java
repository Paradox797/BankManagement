package Billpay;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BillpayServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/webapp1";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        try (PrintWriter out = response.getWriter()) {
            String firstName = request.getParameter("firstName");
            String middleName = request.getParameter("middleName");
            String lastName = request.getParameter("lastName");
            String passport = request.getParameter("passport");
            String location = request.getParameter("location");
            
            
             if (firstName.isEmpty()||middleName.isEmpty() || lastName.isEmpty() || passport.isEmpty() || location.isEmpty()) {
                 
                out.println("Error: All fields are required.");
                return;
            }

            // Check passport length
            if (passport.length() < 3) {
                out.println("Error: Passport length must be at least 3 characters.");
                return;
            }
            //String id=request.getParameter("id");;

            try {
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                    String insertQuery = "INSERT INTO billpay (firstName, middleName, lastName,passport,location) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(insertQuery);
                    // statement.setString(1, id);
                    statement.setString(1, firstName);
                    statement.setString(2, middleName);
                    statement.setString(3, lastName);
                    statement.setString(4, passport);
                    statement.setString(5, location);
                    statement.executeUpdate();
                    out.println("<html><body>");
                  
                    out.println("<h3>Successfully completed</h3>");
                    
                    // Retrieve the auto-generated ID
                    out.println("</body></html>");
                }
            } catch (SQLException e) {
                 e.printStackTrace();
                out.println("Error: " + e.getMessage());
            }
        }
    }

    // Other methods...
}
