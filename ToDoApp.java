import javax.swing.*;
public class ToDoApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoAppGUI();
            }
        });
    }
}