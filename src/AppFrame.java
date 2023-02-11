import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class AppFrame extends JFrame {
    private DefaultListModel<Friend> listModel;
    private FriendsList friends;
    private JList<Friend> friendsJList;
    private JTextField searchNameField;
    private JTextField friendNameField;
    private JTextField friendNumberField;
    private JButton searchButton;
    private JButton addFriend;
    private JButton deleteButton;
    private JButton saveButton;

    public AppFrame() {
        super();
        this.setSize(700, 300);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        friends = new FriendsList();
        addThings();
        this.addWindowListener(new MyWindowListner());


    }

    private void addThings() {
        JPanel rootPanel = new JPanel();
        friendsJList = new JList<>(friends.getFriends().toArray(new Friend[friends.getFriends().size()]));
        JScrollPane scrollPane = new JScrollPane(friendsJList);
        searchNameField = new JTextField(15);
        searchButton = new JButton("search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchNameField.getText();
                List<Friend> searchResults = friends.searchFriends(searchTerm);
                friendsJList.setListData(searchResults.toArray(new Friend[searchResults.size()]));
            }
        });

        JLabel labelName = new JLabel("new friend name:");
        friendNameField = new JTextField(10);

        JLabel labelPhone = new JLabel("new friend phone number:");
        friendNumberField = new JTextField(10);

        addFriend = new JButton("Add new friend");
        addFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = friendNameField.getText();
                String phone = friendNumberField.getText();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    Friend newFriend = new Friend(name,phone);
                    friends.addFriend(newFriend);
                    friendsJList.setListData(friends.getFriends().toArray(new Friend[friends.getFriends().size()]));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please enter a name and phone number.");
                }
            }
        });

        deleteButton = new JButton("Delete friend");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Friend selectedFriend = (friendsJList.getSelectedValue());
                if (selectedFriend != null) {
                    friends.removeFriend(selectedFriend);
                    friendsJList.setListData(friends.getFriends().toArray(new Friend[friends.getFriends().size()]));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Please select the Friend you want to delete.");
                }
            }
        });

        saveButton = new JButton("Save changes");


        rootPanel.add(scrollPane);
        rootPanel.add(searchNameField);
        rootPanel.add(searchButton);
        rootPanel.add(labelName);
        rootPanel.add(friendNameField);
        rootPanel.add(labelPhone);
        rootPanel.add(friendNumberField);
        rootPanel.add(addFriend);
        rootPanel.add(deleteButton);


        this.getContentPane().add(rootPanel);
    }
    class MyWindowListner extends Component implements WindowListener{
        @Override
        public void windowOpened(WindowEvent e) {

        }
        @Override
        public void windowClosing(WindowEvent e) {
            int choice = JOptionPane.showConfirmDialog(this, "do you want to save the changes made?");
            if(choice == JOptionPane.YES_OPTION) {
                friends.saveToFile();
                friends.viewFriends();
                Component appFrame = null;
                JOptionPane.showMessageDialog(appFrame, "your changes has been saved ");
                System.out.println("Goodbye.....");
                System.exit(0);
            }
            else if (choice == JOptionPane.NO_OPTION) {
                Component appFrame = null;
                JOptionPane.showMessageDialog(appFrame, "Your changes were not saved ");
                System.out.println("Goodbye.....");
                System.exit(0);
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}


