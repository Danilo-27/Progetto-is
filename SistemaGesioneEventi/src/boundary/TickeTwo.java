package boundary;


import boundary.BoundaryUtente.HomePage;

import java.awt.*;

public class TickeTwo {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                HomePage frame = new HomePage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
